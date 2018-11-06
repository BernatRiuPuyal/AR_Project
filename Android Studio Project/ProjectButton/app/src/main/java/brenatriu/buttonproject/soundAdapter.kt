package brenatriu.buttonproject

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class SoundAdapter: RecyclerView.Adapter<SoundAdapter.SoundViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SoundViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: SoundViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class SoundViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var soundButton: Button = itemView.soundButton;
        var soundTitle: TextView = itemView.soundText;
    }
}