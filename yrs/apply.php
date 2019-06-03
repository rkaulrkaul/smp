<?php
// INCLUDE ON EVERY TOP-LEVEL PAGE!
include("includes/init.php");
$valid_submit = '';
function print_app_record($record)
{
  echo "<tr>";
  echo "<td>" . $record['net_id'] . "</td>";
  echo "<td>" . $record['year'] . "</td>";
  echo "<td>" . $record['veteran'] . "</td>";
  echo "<td>" . $record['experience'] . "</td>";
  echo "<td>" . $record['strengths'] . "</td>";
  echo "<td>" . $record['why'] . "</td>";
  echo "<td>" . $record['car'] . "</td>";
  echo "</tr>";
}
$current_page = "apply";
?>
<!DOCTYPE html>
<html lang="en">
<?php include('includes/head.php');

$name_error = 'hidden';
$net_error = 'hidden';
$textarea1_error = 'hidden';
$textarea2_error = 'hidden';
$textarea3_error = 'hidden';
$textarea4_error = 'hidden';


$Q1 = "What experience do you have working with children and/or working with under-served communities?";
$Q2 = "What do you feel like you could add to program? What are your strengths?";
$Q3 = "Why do you want to be a YOURS mentor?";
$Q4 = "Do you have a car on campus and if so, would you be willing to be a driver for YOURS?";
?>


<body>

  <?php include('includes/header.php');
  if (is_user_logged_in()) {
    ?>
    <div id='app'>
      <?php
      $app_sql = "SELECT * FROM mentor_apps;";
      $app_params = array();
      $result = exec_sql_query($db, $app_sql, $app_params)->fetchAll();
      if ($result) {
        ?>
        <table>
          <tr>
            <th>Netid</th>
            <th>Year</th>
            <th>Veteran?</th>
            <th>Past Experience</th>
            <th>Strengths</th>
            <th>Why they want to be a mentor</th>
            <th>Car on campus?</th>
          </tr>
          <?php
          foreach ($result as $app) {
            print_app_record($app);
          }
          ?>
        </table>
      </div>
    <?php
  } else {
    echo '<h1>No applications have been submitted</h1>';
  }
} else {
  //USER IS NOT LOGGED IN

  // Was the form submitted?
  if (isset($_POST["submit"])) {
    $valid_submit = TRUE;
    //Check to see if first name and last name are valid
    $fname = $_POST[htmlspecialchars('firstName')];
    $lname = $_POST[htmlspecialchars('lastName')];
    $net = $_POST[htmlspecialchars('netid')];
    // Name is required.
    if ($fname  == '' || $lname == '') {
      $valid_submit = FALSE;
      $name_error = '';
    }
    //NetID is required
    if ($net  == '') {
      $valid_submit = FALSE;
      $net_error = '';
    }
    //Filter textarea
    $exp = $_POST[htmlspecialchars('exp')];
    $strength = $_POST[htmlspecialchars('strength')];
    $why = $_POST[htmlspecialchars('why')];
    $car = $_POST[htmlspecialchars('car')];
    // Check that text area is filled out
    if ($exp  == '' || $strength == '') {
      $valid_submit = FALSE;
      $textarea1_error = '';
    }
    if ($strength == '') {
      $valid_submit = FALSE;
      $textarea2_error = '';
    }
    if ($why  == '') {
      $valid_submit = FALSE;
      $textarea3_error = '';
    }
    if ($car  == '') {
      $valid_submit = FALSE;
      $textarea4_error = '';
    }



    // INSERT THE SUBMISSION INTO THE SUBMISSION TABLE FOR USERS TO SEE
    $year = filter_input(INPUT_POST, 'gradyear', FILTER_SANITIZE_STRING);
    $veteran = filter_input(INPUT_POST, 'previous', FILTER_SANITIZE_STRING);



    $add_app_sql = "INSERT INTO `mentor_apps` (net_id, year, veteran, experience, strengths, why, car) VALUES (:net, :year, :veteran, :exp, :strength, :why, :car);";
    $add_app_params = array(
      ':net' => $net,
      ':year' => $year,
      ':veteran' => $veteran,
      ':exp' => $exp,
      ':strength' => $strength,
      ':why' => $why,
      ':car' => $car
    );
    $add_app_results = exec_sql_query($db, $add_app_sql, $add_app_params)->fetchAll();
  } else {
    //Form was not submitted.
    //Clear form
    $fname = "";
    $lname = "";
    $exp = "";
    $strength = "";
    $why = "";
    $car = "";
    $net = "";
  }

  if ($valid_submit) { ?>
      <h1> Your form has been submitted sucessfully. You can review your submission below.</h1>


      <div class="confirmation">

        <?php
        echo "Name:" . " " . $fname . " " . $lname;
        echo nl2br("\nNetId:" . " \n" . $net);
        echo  nl2br("\n" . $Q1 . ": \n" . $exp);
        echo  nl2br("\n" . $Q2 . ": \n" . $strength);
        echo  nl2br("\n" . $Q3 . ": \n" . $why);
        echo  nl2br("\n" . $Q4 . ": \n" . $car);


        ?>
      </div>
    <?php } else {

    ?>

      <div id='app'>
        <h1>YOURS Mentor Application</h1>

        <form id="appform" method="post" action="apply.php">
          <div id="app_container">
            <div>
              <label class=''>Full Name:</label>
              <input type="text" class="namebox" name="firstName" placeholder="First" value="<?php echo $fname; ?>" />
              <input type="text" class="namebox inline" name="lastName" placeholder="Last" value="<?php echo $lname; ?>" />
              <p class='error_msg <?php echo $name_error ?>'>Please enter your full name.</p>
            </div>

            <div>
              <label>Net ID:</label>
              <input class='inline' type="text" name="netid" value="<?php echo $net; ?>" placeholder="NetID" />
              <p class='error_msg <?php echo $net_error ?>'>Please type your NetID.</p>
            </div>

            <div>
              <label>What year are you at Cornell?:</label>
              <div class="radio">
                <input type="radio" id="yearFreshman" name="gradyear" value="Freshman" checked>Freshman
              </div>
              <div class="radio">
                <input type="radio" id="yearSophomore" name="gradyear" value="Sophomore">Sophomore
              </div>
              <div class="radio">
                <input type="radio" id="yearJunior" name="gradyear" value="Junior">Junior
              </div>
              <div class="radio">
                <input type="radio" id="yearSenior" name="gradyear" value="Senior">Senior
              </div>
            </div>

            <div>
              <label>Have you previously been a YOURS mentor?:</label>
              <div class="radio">
                <input type="radio" name="previous" value="Yes">Yes
              </div>
              <div class="radio">
                <input type="radio" name="previous" value="No" checked>No
              </div>
            </div>
            <div>
              <label class='inline'><?php echo $Q1 ?></label>
              <p class='error_msg  <?php echo $textarea1_error ?>'>This question is required.</p>
              <textarea class='textbox' name="exp" value="<?php echo $exp; ?>"></textarea>
            </div>
            <div>
              <label class='inline'><?php echo $Q2 ?></label>
              <p class='error_msg  <?php echo $textarea2_error ?>'>This question is required.</p>
              <textarea class='textbox' name="strength" value="<?php echo $strength; ?>"></textarea>
            </div>
            <div>
              <label class='inline'><?php echo $Q3 ?></label>
              <p class='error_msg  <?php echo $textarea3_error ?>'>This question is required.</p>
              <textarea class='textbox' name="why" value="<?php echo $why; ?>"></textarea>
            </div>
            <div>
              <label class='inline'><?php echo $Q4 ?></label>
              <p class='error_msg  <?php echo $textarea4_error ?>'>This question is required.</p>
              <textarea class='textbox' name="car" value="<?php echo $car; ?>"></textarea>
            </div>
            <input id="app_submit" type="submit" name="submit" value="Submit" <?php $finish = "done"; ?> />
          </div>
        </form>
      <?php } ?>
    </div>
  <?php }
?>
  <?php
  include('includes/footer.php');
  ?>
</body>


</html>
