<?php
// INCLUDE ON EVERY TOP-LEVEL PAGE!
include("includes/init.php");
$current_page = "images";


$photoid = $_GET["id"];
$albumid = $_GET['album_name'];

//function that prints records
function print_record($record)
{
  echo ("<div id='solo_image'> <img src = 'uploads/images/" . $record["id"] . $record["file_ext"] . "' alt = '" . $record["file_name"] . "' ></div>");

}

$sqlfileext = "SELECT file_ext FROM images WHERE images.id = :image_id";
$params = array(
  ':image_id' => $photoid,
);
$resultfileext = exec_sql_query($db, $sqlfileext, $params)->fetchAll();

$fileext = $resultfileext[0]['file_ext'];

//getalbumid
$sqlalbumid = "SELECT album_id FROM image_album WHERE image_album.image_id = :image_id";
$params = array(
  ':image_id' => $photoid,
);
$resultalbumid = exec_sql_query($db, $sqlalbumid, $params)->fetchAll();

$albumid = $resultalbumid[0]['album_id'];


//gets albumname
$sqlalbum = "SELECT album_name FROM albums INNER JOIN image_album ON albums.id = image_album.album_id INNER JOIN images ON images.id = image_album.image_id WHERE image_album.image_id = :image_id";
$params = array(
  ':image_id' => $photoid,
);
$resultalbum = exec_sql_query($db, $sqlalbum, $params)->fetchAll();

$albumname = $resultalbum[0]['album_name'];

?>
<!DOCTYPE html>
<html lang="en">

<?php include('includes/head.php'); ?>

<body>
<?php include('includes/header.php');
?>
<div id = "image_main">
  <?php
  if (isset($_POST['delete_photo'])) {
    $photoid = $_POST['delete_photo'];
    $sqlphoto = "DELETE FROM images WHERE id = :image_id";
    $params = array(
      ':image_id' => $photoid,
    );
    $resultphotos = exec_sql_query($db, $sqlphoto, $params);

    $file_name = "uploads/images/$photoid$fileext";
    $unlink = unlink($file_name);
    if ($unlink) {
      echo ("<p>This photo was successfully deleted!</p>");
    }
    $result = NULL;
  }  else {//get the single image and description
    $sql = "SELECT * FROM images WHERE images.id = :image_id";
    $params = array(
      ':image_id' => $photoid,
    );
    $result = exec_sql_query($db, $sql, $params)->fetchAll();
    echo "<div id = 'l_img'>";
    if ($result) {
      foreach ($result as $record) {
        print_record($record);
      }
    } else {
      echo "Image deleted";
    }
    echo "</div>";
    echo "<div id = 'r_img'><div id = 'button_containers'><button id='link'><a href='albums.php?id=" . $albumid . "&album_name=" . $albumname . "'>Return to " . $albumname . "</a></button>";
    if (is_user_logged_in() ) {

      echo "<form id ='delete_photo' action = 'image.php?id=$photoid' method = 'post'>";
      echo "<button id = 'del_button' name = 'delete_photo' type = 'submit' value =$photoid >Delete this Photo</button></form>";
    }
    echo "</div></div>";
  }
  ?>
</div>

<?php include('includes/footer.php');
  ?>

</body>

</html>
