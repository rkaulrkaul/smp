<?php
// vvv DO NOT MODIFY/REMOVE vvv

// check current php version to ensure it meets 2300's requirements
function check_php_version()
{
  if (version_compare(phpversion(), '7.0', '<')) {
    define(VERSION_MESSAGE, "PHP version 7.0 or higher is required for 2300. Make sure you have installed PHP 7 on your computer and have set the correct PHP path in VS Code.");
    echo VERSION_MESSAGE;
    throw VERSION_MESSAGE;
  }
}
check_php_version();

function config_php_errors()
{
  ini_set('display_startup_errors', 1);
  ini_set('display_errors', 0);
  error_reporting(E_ALL);
}
config_php_errors();

// open connection to database
function open_or_init_sqlite_db($db_filename, $init_sql_filename)
{
  if (!file_exists($db_filename)) {
    $db = new PDO('sqlite:' . $db_filename);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (file_exists($init_sql_filename)) {
      $db_init_sql = file_get_contents($init_sql_filename);
      try {
        $result = $db->exec($db_init_sql);
        if ($result) {
          return $db;
        }
      } catch (PDOException $exception) {
        // If we had an error, then the DB did not initialize properly,
        // so let's delete it!
        unlink($db_filename);
        throw $exception;
      }
    } else {
      unlink($db_filename);
    }
  } else {
    $db = new PDO('sqlite:' . $db_filename);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    return $db;
  }
  return null;
}

function exec_sql_query($db, $sql, $params = array())
{
  $query = $db->prepare($sql);
  if ($query and $query->execute($params)) {
    return $query;
  }
  return null;
}

// ^^^ DO NOT MODIFY/REMOVE ^^^

// You may place any of your code here.


//establishing connection to database
$db = open_or_init_sqlite_db('secure/yours_db.sqlite', 'secure/init.sql');

define('SESSION_COOKIE_DURATION', 60*60*1); // Sets a cookie for an hour's time
$session_errors = array();
$logger = "Login";

function log_in($username, $password) {
  global $db;
  global $current_user;
  global $session_errors;

   // Source: Kyle Harms: Lab 8
  if (isset($username) && isset($password)) {
    $sql = "SELECT * FROM users WHERE username = :username;"; // Selects all users (only 1 since unique) that have same username as the one in parameter
    $params = array(
      ':username' => $username
    );
    $records = exec_sql_query($db, $sql, $params)->fetchAll(); // executes query with the parameters and puts into records
    // Check if sql query was succesful and if $records has the needed information
    if ($records) {
      $account = $records[0]; // Username is a unique field, so only 1 record should be selected. Store in variable for ease
      if (password_verify($password, $account['password'])) {
        $session = session_create_id(); // Creates a session with an ID an stores it into variable
        $sql = "INSERT INTO sessions (user_id, session) VALUES (:user_id, :session);"; // Store ID into sessions database
        $params = array(
          ':user_id' => $account['id'],
          ':session' => $session
        );
        $result = exec_sql_query($db, $sql, $params);
        if ($result) {
          // If $result query succeeds, session stored in sessions table
          // Send this back to the user.
          setcookie("session", $session, time() + SESSION_COOKIE_DURATION); // Sets cookie with the specified time above
          header('Location: index.php'); // redirects to home page upon login
          return $current_user;
        } else {
          array_push($session_errors, "Login failed.");
        }
      } else {
        array_push($session_errors, "Invalid username/password.");
      }
    } else {
      array_push($session_errors, "Invalid username/password.");
    }
  } else {
    array_push($session_errors, "Invalid username/password");
  }
  // if nothing is returned, current user is set to null and null is returned.
  $current_user = NULL;
  return NULL;
}

// finds user and returns its record
function find_user($user_id) {
  global $db;
  $sql = "SELECT * FROM users WHERE id = :user_id;";
  $params = array(
    ':user_id' => $user_id
  );
  $records = exec_sql_query($db, $sql, $params)->fetchAll();
  if ($records) {
    // user_id is a unique field, so only 1 record should be selected.
    return $records[0];
  }
  // if nothing is returned earlier, then return NULL
  return NULL;
}

// finds session and returns its record
// Source: Kyle Harms: Lab 8
function find_session($session) {
  global $db;

  if (isset($session)) {
    $sql = "SELECT * FROM sessions WHERE session = :session;";
    $params = array(
      ':session' => $session
    );
    $records = exec_sql_query($db, $sql, $params)->fetchAll();
    if ($records) {
      // Sessions is a unique field, so only 1 record should be selected.
      return $records[0];
    }
  }
  // if nothing is returned earlier, then return NULL
  return NULL;
}

// Logs in by checking to see if a cookie is currently set. Renews it if set.
// Source: Kyle Harms: Lab 8
function session_login() {
  global $db;
  global $current_user;

  if (isset($_COOKIE["session"])) {
    $session = $_COOKIE["session"]; // variable for ease
    $session_record = find_session($session); // use find_session function which returns a session from database
    // check if session_record is not null
    if (isset($session_record)) {
      $current_user = find_user($session_record['user_id']); // use find_user function which returns the user which matches user_id in session_record
      setcookie("session", $session, time() + SESSION_COOKIE_DURATION); // Renew the cookie for another hour
      return $current_user;
    }
  }
  // if nothing is returned, then set current user to null and return null
  $current_user = NULL;
  return NULL;
}

// Source: Kyle Harms: Lab 8
function is_user_logged_in() {
  global $current_user;

  return ($current_user != NULL);   // if $current_user is not NULL, return true, else false
}

// Source: Kyle Harms: Lab 8
function log_out() {
  global $current_user;
  setcookie('session', '', time() - SESSION_COOKIE_DURATION); // Expire the cookie by subtracting time.
  $current_user = NULL;
}

// Check for login, logout, and when to keep the user logged in.
// Source: Kyle Harms: Lab 8
// Check if we should login the user (only if login, username, and password are set)
if (isset($_POST['login']) && isset($_POST['username']) && isset($_POST['password'])) {
  // trim both username and password field inputs
  $username = trim($_POST['username']);
  $password = trim($_POST['password']);
  log_in($username, $password); // call log_in function with specified inputs from form
} else {
  // Check if logged in already with session_login function. 
  session_login();
}

// Source: Kyle Harms: Lab 8
// Check if we should logout the user- sees if current user is set and there is a logout request
if (isset($current_user) && (isset($_GET['logout']) || isset($_POST['logout']))) {
  log_out();
}

?>
