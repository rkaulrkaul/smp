<?php
if (is_user_logged_in()) {
  ?>
  <div id="uploaddiv">

    <!-- <p>Upload a Photo To This Album</p> -->

    <form id="uploadForm" action=<?php echo "'" . $self . $query_strings . "'"; ?> method="post" enctype="multipart/form-data">
      <!-- <ul> -->
      <!-- <li> -->
      <input class='inline' type="hidden" name="MAX_FILE_SIZE" value="<?php echo MAX_FILE_SIZE; ?>" />
      <label for="photo_file">Upload a Photo To This Album:</label>
      <input id="photo_file" type="file" name="photo_file">
      <!-- </li> -->
      <!-- <li> -->
      <button class='inline' name="submit_upload" type="submit">Upload Photo</button>
      <!-- </li> -->
      <!-- </ul> -->
    </form>
  </div>
<?php
} else {

  echo "<p class='bold' id='loginreminder'>Want to upload a photo to this album? Members, please log in first!</p>";
} ?>
