<?php
/**
 * Plugin Name: Add Announcement To Post
 * Plugin URI: https://example.com/plugins/Add Announcement To Post/
 * Description: Adds a random announcement to post
 * Version: 1.0
 * Requires at least: 5.0
 * Requires PHP: 7.2
 * Author: Marek Schubert, Le Van Hien
 * License: GPL v2 or later
 * License URI: https://www.gnu.org/licenses/gpl-2.0.html
 */

 function aatp_admin_actions_register_menu(){
    add_options_page("Add Announcement To Post", "Add Announcement To Post", 'manage_options', "aatp", "aatp_admin_page");
   }
   
add_action('admin_menu', 'aatp_admin_actions_register_menu'); 
   
function aatp_admin_page(){
    // get _POST variable from globals
    global $_POST;
    // process changes from form
    if(isset($_POST['aatp_do_change'])){
        if($_POST['aatp_do_change'] == 'Y'){
            $new_announcement = $_POST['announcement_text'];
            //echo $new_announcement;
            echo '<div class="notice notice-success isdismissible"><p>Settings saved.</p></div>';
            //do_action('aatp_save_new_announcement_to_database',$new_announcement);
            //aatp_save_new_announcement_to_database($new_announcement);
            // update_option('announcement_text', $opDays);
            
            global $wpdb;

            $date= current_time('mysql');

            $table = $wpdb->prefix.'announcements';
            $data = array('html_text' => $new_announcement);
            $format = array('%s');

            $wpdb->insert($table,$data,$format);
        }
    }
    
    //read current option value
    $opDays = get_option('naph_days');
    //display admin page
    ?>

    <div class="wrap">
        <h1>Newly Added Post Highliter</h1>
        <form name="aatp_form" method="post">
            <input type="hidden" name="aatp_do_change" value="Y">
            <p>Add html code of a new announcement:</p>
            <textarea rows = "4" cols = "40" name = "announcement_text">Enter details here...</textarea>
            <p class="submit"><input type="submit" value="Submit"></p>
        </form>
    </div>

    <?php
} 
       
function aatp_mark_new_post_title($content, $id){
    global $wpdb;

    $posts = $wpdb->get_results('SELECT html_text FROM wp_announcements;');
    $number_of_announcements = count($posts);

    if($number_of_announcements < 1)
    {
        return $content;
    }

    $randValue = rand()%$number_of_announcements;

    $random_announcement = $posts[$randValue];

    return $content."<p class=\"naph_marker\">$random_announcement->html_text</p>";
}

add_filter('the_title', 'aatp_mark_new_post_title', 10, 2);

function aatp_register_styles(){
    //register style
    wp_register_style('naph_styles', plugins_url('style.css', __FILE__));
    //enable style (load in meta of html)
    wp_enqueue_style('naph_styles');
}

add_action('init', 'aatp_register_styles');

register_activation_hook(__FILE__, 'createAnnouncementsDatabase');
function createAnnouncementsDatabase() {
    global $wpdb;
	$charset_collate = $wpdb->get_charset_collate();
	$table_name = $wpdb->prefix . 'announcements';

    $drop_table = "DROP TABLE IF EXISTS $table_name;";
    $wpdb->query($drop_table);

	$create_table = "CREATE TABLE $table_name (
		html_text VARCHAR(200)
	) $charset_collate;";
	require_once( ABSPATH . 'wp-admin/includes/upgrade.php' );
    dbDelta( $create_table );
}
?>