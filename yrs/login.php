<?php
include("includes/init.php");
$current_page = "login";
?>
<!DOCTYPE html>
<html lang="en">
<?php include('includes/head.php'); ?>

<body>
    <?php include("includes/header.php"); ?>
    <?php if (!is_user_logged_in()) { ?>
        <div id="loginpage">
            <h1>Log in</h1>
            <form id="login_form" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
                <ul>
                    <li>
                        <label for="username">Username:</label>
                        <input id="username" type="text" name="username" />
                    </li>
                    <li>
                        <label for="password">Password:</label>
                        <input id="password" type="password" name="password" />
                    </li>
                    <li>
                        <button name="login" type="submit">Sign In</button>
                    </li>
                </ul>
            </form>
        </div>
    <?php } else { ?>
        <h1>Signed in as <?php echo htmlspecialchars($current_user['username']); ?> </h1>
        <!-- more stuff can be done here if needed -->

    <?php }

include('includes/footer.php');
?>
</body>

</html>
