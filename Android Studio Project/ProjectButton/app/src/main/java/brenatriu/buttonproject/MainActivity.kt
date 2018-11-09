package brenatriu.buttonproject

import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    var mediaPlayer : MediaPlayer? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var jsonString = resources.openRawResource(R.raw.sounds).bufferedReader().use{it.readText()}

        var gson = Gson()
        val soundList = gson.fromJson(jsonString,SoundList::class.java)

        soundList.sounds?.let {


            soundsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView)
            soundsRecyclerView.adapter = SoundAdapter(it)
            //adapter.onSoun
        }



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
//        button1.setOnClickListener{
//
//            mediaPlayer?.release();
//            var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.snd_shoot)
//            mediaPlayer?.start() // no need to call prepare(); create() does that for you
//
//        }
//        button2.setOnClickListener{
//
//            mediaPlayer?.release();
//            var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.explosion3)
//            mediaPlayer?.start() // no need to call prepare(); create() does that for you
//
//        }


    }

    override fun onItemClick(sound: SoundModel, position: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, resources.getIdentifier(sound.file,"raw", packageName))
        mediaPlayer?.start()
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
