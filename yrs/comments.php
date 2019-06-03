<?php
// INCLUDE ON EVERY TOP-LEVEL PAGE!
include("includes/init.php");
$comment_error = 'hidden';
$email_error = 'hidden';
$messages = array();
$current_page = "comments";
$programs = array();
$email = '';
$comment = '';
$result = '';
if (isset($_POST['submit_feedback'])) {
  $valid_comment = TRUE;
  $email = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_STRING);
  $comment = $_POST[htmlspecialchars('comment')];
  $program = filter_input(INPUT_POST, 'program', FILTER_SANITIZE_STRING);

  if (strlen($comment) == 0) {
    $valid_comment = FALSE;
    $comment_error = " ";
  }
  if (strlen($email) == 0) {
    $valid_comment = FALSE;
    $email_error = " ";
  }
  if ($valid_comment) {
    $add_comment_sql = "INSERT INTO `feedback` (email, program, comment) VALUES (:email, :program, :comment);";

    $add_comment_params = array(
      ':email' => $email,
      ':program' => $program,
      ':comment' => $comment
    );
    $comment_result = exec_sql_query($db, $add_comment_sql, $add_comment_params);
  }
  //   if ($comment_result) {
  //     array_push($messages, "Your review has been recorded. Thank you!");
  //   } else {
  //     array_push($messages, "Failed to add review.");
  //   }
  // } else {
  //   array_push($messages, "Failed to add review. Invalid product or rating.");
  // }
}

//Lab:08
//Search Form
const SEARCH_FIELDS = [
  "email" => "By Reviewer",
  "program" => "By Program",
  "comment" => "By Suggestion/Comment"
];

if (isset($_GET['search']) && isset($_GET['category'])) {
  $do_search = TRUE;

  $category = filter_input(INPUT_GET, 'category', FILTER_SANITIZE_STRING);
  if (in_array($category, array_keys(SEARCH_FIELDS))) {
    $search_field = $category;
  } else {
    array_push($messages, "Invalid category for search.");
    $do_search = FALSE;
  }

  $search = filter_input(INPUT_GET, 'search', FILTER_SANITIZE_STRING);
  $search = trim($search);
} else {
  // No search provided
  $do_search = FALSE;
  $category = NULL;
  $search = NULL;
}

function print_comment_row($record)
{
  echo "<tr>";
  echo "<td>" . $record['email'] . "</td>";
  echo "<td>" . $record['program'] . "</td>";
  echo "<td>" . $record['comment'] . "</td>";
  echo "</tr>";
}
?>
<!DOCTYPE html>
<html lang="en">
<?php include('includes/head.php'); ?>


<body>
  <?php include('includes/header.php');

  ?>
  <div id='comment-main'>

    <h1>Send Us Suggestions!</h1>
    <!-- <p>We love to improve our programs to benefit the community we serve. Please leave us a comment/suggestion below!</p> -->
    <div id='side'>
      <div id="forms">
        <?php
        if (is_user_logged_in()) {
          ?>
          <div id="addprogram">
            <h2 class='center'>Want to add a program?</h2>
            <form id="addprogramForm" method="post" action="comments.php">
              <input type='text' id='program' name='program'>
              <button name="submit" type="submit">Submit</button>
            </form>
          </div>
          <?php
          if (isset($_POST['submit'])) {
            $valid_program = TRUE;
            $program = $_POST[htmlspecialchars('program')];
            if (strlen($program) == 0) {
              $valid_program = FALSE;
            }
            if ($valid_program) {
              $add_program_sql = "INSERT INTO `programs` (name) VALUES (:program);";
              $add_program_params = array(
                ':program' => $program
              );
              $program_result = exec_sql_query($db, $add_program_sql, $add_program_params);
            }
          }
        }
        ?>
        <form id="FeedbackForm" method="post" action="comments.php">
          <h2 class='center'>Submit Feedback</h2>
          <!-- <li> -->
          <p class='error_msg  <?php echo $email_error ?>'>This section is required.</p>
          <label class='inline bold' for='email'>Email:</label>
          <input type='email' id='email' name='email' value="<?php echo $email; ?>">
          <!-- </li> -->
          <p class='bold'>Program Name:</p>
          <ul>
            <li>
              <input class='inline' id='mon_program' value='Monday Makers' type='radio' name='program' checked>
              <label class='inline' for='program'>Monday Makers</label>
            </li>
            <?php
            $add_program_sql = "SELECT * FROM `programs`";
            $program_names_params = array();
            $program_result = exec_sql_query($db, $add_program_sql, $program_names_params)->fetchAll();

            if ($program_result) {
              foreach ($program_result as $prog) {
                echo "<li><input class ='inline' id ='" . $prog['name'] . "' value ='" . $prog['name'] . "' type = 'radio' name = 'program'>" . $prog['name'] . "</li>";
              }
            }
            ?>
          </ul>
          <label for='comment' class='bold'>Suggestion/Comment:</label>
          <p class='error_msg  <?php echo $comment_error ?>'>This section is required.</p>
          <textarea id='comment' name='comment' value="<?php echo $comment; ?>" col='100' row='4'></textarea>
          <p><button name="submit_feedback" type="submit">Submit Feedback</button></p>
        </form>
      </div>

      <?php

      ?>
      <!-- FOR EACH SUGGESTION RECORD IN DATABASE -->
      <!-- </table> -->
      <div id='with-table'>
        <h2 class='center'>Search for a Suggestion/Comment!</h2>

        <!--Lab:08-->
        <!--Search Form-->
        <form class='center' id="searchForm" action="comments.php" method="get">
          <select name="category">
            <option value="" selected disabled>Search By</option>
            <?php
            foreach (SEARCH_FIELDS as $field_name => $label) {
              ?>
              <option value="<?php echo $field_name; ?>"><?php echo $label; ?></option>
            <?php
          }
          ?>
          </select>
          <input type="text" name="search" />
          <button type="submit">Search</button>
        </form>

        <?php
        if ($do_search) {
          ?>
          <h2>Search Results</h2>
          <?php
          $sql = "SELECT * FROM feedback WHERE " . $search_field . " LIKE '%' || :search || '%'";
          $params = array(
            ':search' => $search
          );
          $result = exec_sql_query($db, $sql, $params);
        } else {

          ?>
          <table>
            <tr>
              <th>Reviewer Email</th>
              <th>Program</th>
              <th>Suggestion/Comment</th>
            </tr>
            <?php
            $feedbacksql = "SELECT * FROM feedback";
            $feedbackparams = array();
            $feedbackresults = exec_sql_query($db, $feedbacksql, $feedbackparams)->fetchAll();
            if ($feedbackresults) {
              foreach ($feedbackresults as $feedback) {
                print_comment_row($feedback);
              }
            }
            ?>
          </table>

        <?php

      }

      if ($result) {
        $records = $result->fetchAll();

        if (count($records) > 0) {
          ?>
            <table>
              <tr>
                <th>Reviewer Email</th>
                <th>Program</th>
                <th>Suggestion/Comment</th>
              </tr>

              <?php
              foreach ($records as $record) {
                print_comment_row($record);
              }
              ?>
            </table>
          <?php
        } else {
          echo "<p>No matching suggestions/comments found.</p>";
        }
      }
      ?>
      </div>
    </div>
  </div>



  <?php include('includes/footer.php'); ?>
</body>

</html>
