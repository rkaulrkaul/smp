<?php
// INCLUDE ON EVERY TOP-LEVEL PAGE!
include("includes/init.php");
$current_page = "albums";
if (isset($_GET['id'])) {
  $id = $_GET['id'];
  $get_name_sql = "SELECT album_name FROM albums WHERE id=" . $id . ";";
  $get_name_params = array();
  $get_name_result = exec_sql_query($db, $get_name_sql, $get_name_params)->fetchAll();
  if ($get_name_result) {
    //there should only be one with that id
    $current_album_name = $get_name_result[0][0];
  }
} else {
  $id = NULL;
}
$self = substr($_SERVER['PHP_SELF'], 1);
if ($id == NULL) {
  $query_strings = '';
} else {
  $query_strings = '?' . $_SERVER['QUERY_STRING'];
}
//lists the names of the albums as links with query string parameters to the same page
function list_album_links($record)
{
  global $id;
  if (!isset($_GET['id']) && $record['id'] == 1) {
    echo "<li class='current_album'><a href='albums.php?id=" . $record['id'] . "'>" . $record[1] . "</a></li>";
  } elseif ($record['id'] == $id) {
    echo "<li class='current_album'><a href='albums.php?id=" . $record['id'] . "'>" . $record[1] . "</a></li>";
  } else {
    echo "<li><a href='albums.php?id=" . $record['id'] . "'>" . $record[1] . "</a></li>";
  }
}


function print_photo($record)
{

  echo "<div class ='photo_box '><a href='image.php?id=" . $record['image_id'] . "'><img src='uploads/images/" . $record['image_id'] . $record['file_ext'] . "' alt='" . $record['file_name'] . "'/></a>";
  echo "</div>";
}
if (isset($_POST['add_album'])) {
  $new_album_name = filter_input(INPUT_POST, 'new_album_name', FILTER_SANITIZE_STRING);

  if (strlen($new_album_name) > 0) {
    $add_album_sql = "INSERT INTO `albums` (album_name) VALUES (:new_album_name);";
    $add_album_params = array(
      ':new_album_name' => $new_album_name
    );
    $result1 = exec_sql_query($db, $add_album_sql, $add_album_params);
  } else {
    echo "<p>the album name was not valid.</p>";
    echo "<p>album name:" . $new_album_name . "</p>";
  }
}
//Source: INFO 2300 Lab 08
//upload
const MAX_FILE_SIZE = 1000000;
if (isset($_POST["submit_upload"]) && is_user_logged_in()) {

  $upload_info = $_FILES["photo_file"];

  if ($upload_info['error'] == UPLOAD_ERR_OK) {

    $upload_name = basename($upload_info["name"]);

    $upload_ext = '.' . strtolower(pathinfo($upload_name, PATHINFO_EXTENSION));

    //adding image into images table
    $sql = "INSERT INTO images (file_name, file_ext) VALUES (:upload_name, :upload_ext);";
    nl2br("\n");
    $params = array(
      ':upload_name' => $upload_name,
      ':upload_ext' => $upload_ext,
    );
    $result = exec_sql_query($db, $sql, $params);
    //get image id
    if ($result) {
      $new_id = $db->lastInsertId("id");
      $new_path = "uploads/images/" . $new_id . $upload_ext;
      move_uploaded_file($_FILES["photo_file"]["tmp_name"], $new_path);
    } else {
      echo "<p>The image did not upload correctly</p>";
    }
    //adding image into this specific album
    if ($id == NULL) {
      $sql2 = "INSERT INTO image_album (image_id, album_id) VALUES (:new_id, 1);";
      $params2 = array(
        ':new_id' => $new_id,
      );
    } else {
      $sql2 = "INSERT INTO image_album (image_id, album_id) VALUES (:new_id, :album_id);";
      $params2 = array(
        ':new_id' => $new_id,
        ':album_id' => $id,
      );
    }
    $albumresult = exec_sql_query($db, $sql2, $params2);
  } else {
    echo "There was an error with the upload";
  }
}
?>
<!DOCTYPE html>
<html lang="en">

<?php include('includes/head.php'); ?>


<body id='albumbody'>
  <?php include('includes/header.php');
  ?>

  <div id='main2'>
    <div id='left2'>
      <aside>
        <h2>Albums</h2>
        <div>
          <?php if (is_user_logged_in()) {
            ?>
            <form id='add_album' method='POST' action='albums.php'>
              <p>Create New Album</p>
              <label for='new_album_name'>Album Name:</label>
              <input id='new_album_name' name='new_album_name' type='text' />
              <button name='add_album'>Create Album</button>
            </form>
          <?php }
        ?>
        </div>
        <ul>
          <?php
          $album_names_sql = "SELECT * FROM `albums`;";
          $album_names_params = array();
          $album_names_results = exec_sql_query($db, $album_names_sql, $album_names_params)->fetchAll();
          if ($album_names_results) {
            foreach ($album_names_results as $album_name) {
              list_album_links($album_name);
            }
          }
          ?>

        </ul>
      </aside>
    </div>
    <div id='right2'>
      <div id='above-album-box'>
        <?php if (!isset($_GET['id'])) { ?>
          <h2>Spring 2019 Album</h2>
          <?php
          include('includes/upload_image.php');
          echo '</div>'; //closes above-album-box if id set

          ?>
          <div id='album_box'>
            <?php
            $photos_sql = "SELECT * FROM images INNER JOIN image_album ON (image_album.image_id=images.id AND image_album.album_id=1);";
            $photos_params = array();
            $photos_results = exec_sql_query($db, $photos_sql, $photos_params)->fetchAll();
            if ($photos_results) {
              foreach ($photos_results as $photo) {
                print_photo($photo);
              }
            } ?>
          </div>
        <?php
      } else {
        echo "<h2>" . $current_album_name . " Album</h2>";
        include('includes/upload_image.php');
        echo '</div>'; //closes above-album-box if id set

        ?>
          <div id='album_box'>
            <?php
            //use join to get all images in the album
            $photos_sql = "SELECT * FROM images INNER JOIN image_album ON (image_album.image_id=images.id AND image_album.album_id=" . $id . ");";
            $photos_params = array();
            $photos_results = exec_sql_query($db, $photos_sql, $photos_params)->fetchAll();
            if ($photos_results) {
              foreach ($photos_results as $photo) {
                print_photo($photo);
              }
            }
            ?>
          </div>

        <?php } ?>
      </div>
    </div>
    <?php include('includes/footer.php');
    ?>
</body>


</html>
