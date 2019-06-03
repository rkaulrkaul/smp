<header>
  <div id='thelogo'>
  <img  id='logo' src='images/logo.jpg' alt='The Y.O.U.R.S. logo'>
  </div>
  <nav>
    <ul>

      <?php
      $list_array = array("index"=> "Home", "albums"=>"Photo Albums", "apply"=>"Apply to be a Mentor", "comments"=>"Submit Feedback");
      foreach ($list_array as $li => $li_name) {
        if ($current_page == $li) {
          echo "<li id = 'current_nav'><a href='" . $li . ".php'>" . $li_name . "</a></li>";
        } else {
          echo "<li id = '" . $li . "'><a href='" . $li . ".php'>" . $li_name . "</a></li>";
        }
      }
      if (is_user_logged_in()) {
        $user =  htmlspecialchars($current_user['username']);
        // Add a logout query string parameter

        $logout_url = htmlspecialchars($_SERVER['PHP_SELF']) . '?' . http_build_query(array('logout' => ''));
        echo '<li class = "hover" id= "login"><a href="' . $logout_url . '">Sign Out: ' . $user . '</a></li>';
      } else {
        if ($current_page == "login") {
          echo '<li class = "hover" id= "current_nav"><a href="login.php">Login</a></li>';
        }
        else {
          echo '<li class = "hover" id= "login"><a href="login.php">Login</a></li>';
        }
      }
      ?>
    </ul>
  </nav>
</header>
