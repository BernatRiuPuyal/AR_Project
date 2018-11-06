package brenatriu.buttonproject

import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var mediaPlayer : MediaPlayer? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        soundsRecyclerView.layoutManager = LinearLayoutManager(this)
        //soundsRecyclerView.adapter =


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        button1.setOnClickListener{

            mediaPlayer?.release();
            var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.snd_shoot)
            mediaPlayer?.start() // no need to call prepare(); create() does that for you

        }
        button2.setOnClickListener{

            mediaPlayer?.release();
            var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.explosion3)
            mediaPlayer?.start() // no need to call prepare(); create() does that for you

        }


    }

    override fun onStop() {
        super.onStop()
        mediaPlayer = null;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
