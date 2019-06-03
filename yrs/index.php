<?php
// INCLUDE ON EVERY TOP-LEVEL PAGE!
include("includes/init.php");
$current_page = "index";
?>
<!DOCTYPE html>
<html lang="en">
<?php include('includes/head.php');

$messages = array();
if (isset($_POST['submit_announcement'])) {
  $valid_announcement = TRUE;
  $name = filter_input(INPUT_POST, 'name', FILTER_SANITIZE_STRING);
  $announcement = filter_input(INPUT_POST, 'announcement', FILTER_SANITIZE_STRING);

  if (strlen($name) == 0 || strlen($comment) == 0) {
    $valid_annoucement = FALSE;
  }

  if ($valid_announcement) {
    $add_announcement_sql = "INSERT INTO `announcements` (name, announcement) VALUES (:name, :announcement);";

    $add_announcement_params = array(
      ':name' => $name,
      ':announcement' => $announcement
    );
    $announcement_result = exec_sql_query($db, $add_announcement_sql, $add_announcement_params);
  }

}

if (isset($_POST['delete_announcement'])) {
  $del_id = filter_input(INPUT_POST, 'delete_announcement', FILTER_SANITIZE_STRING);

  $del_announcement_sql = "DELETE FROM `announcements` WHERE id = :del_id";
  $del_params = array(
    ':del_id' => $del_id,
  );
  $del_res =  exec_sql_query($db, $del_announcement_sql, $del_params);
}


?>

<body>
  <?php include('includes/header.php');
  ?>

  <div id='main'>
    <div id='left'>
      <aside>
        <div id = "div1">
        <div class = "card-title">
        <h2>Announcements</h2>
        </div>
        <p>Check here for announcements from our E-Board!</p>
        </div>
        <div>
            <?php
            $sql = "SELECT * FROM announcements";
            $params = array();
            $records = exec_sql_query($db, $sql, $params)->fetchAll();
            if ($records) {
              foreach ($records as $record) {
                echo "<div id='announcebox'> ";
                echo $record['announcement'];
                echo "<p>-" . $record['name'] . "</p>";
                echo "</div>";
                if (is_user_logged_in()) {
                echo '<form action="index.php" method="post" id="del_announcement">';
                echo "<button id = 'del_anno' name='delete_announcement' type='submit' value ='" . $record['id'] . "'>Delete Announcement</button></form>";
              }}
            }
            ?>
        <div>
      <?php if (is_user_logged_in()) { ?>
      <div>
      <form id="announcement" method="post" action="index.php">
        <h2 class='center'>Make an Announcement</h2>
        <div><label class='inline bold' >Name:</label></div>
        <div><input type='name' id='name' name='name'></div>
        <div><label for='announcement' class='bold'>Announcement:</label></div>
        <div><textarea id='announcement' name='announcement' col='100' row='4'></textarea></div>
        <div><button name="submit_announcement" type="submit">Create Announcement</button></div>
      </form>
      </div>
      <?php } ?>
      </aside>

    </div>

    <div id='right'>

      <div id= "top">
        <img src="images/yourscover.png" alt="YOURS-cover"><figcaption id='frontphoto'>Source: Cornell YOURS Facebook</figcaption>
      </div>

      <div class = "card" id = "bottom" >
      <div id = "div2">
      <div class = "card-title">
      <h2>About</h2>
      </div>
      <p>Youth Outreach Undergraduates Reshaping Success (YOURS) offers after school programs four days a week to youth residing in the mobile homes of Dryden, NY.</p>
      </div>
      <div id = "div3">
      <div class = "card-title">
      <h2>Mission</h2>
      </div>
      <p>Youth Outreach Undergraduates Reshaping Success (YOURS) is a volunteer program that works with youth in Dryden, NY through mentoring, games, crafts, outdoor activities, and field trips.</p>
      </div>
      <div id = "div4">
      <div class = "card-title">
      <h2>Donate!</h2>
      </div>
      <p> We are currently running a crowdfunding campaign to help support our efforts in the community. To learn more and contribute, <a href='https://crowdfunding.cornell.edu/project/9781'>click here</a></p>
      </div>
    </div>
    </div>
      </div>

</body>

<?php include('includes/footer.php');
?>

</html>
