package utilerias;

import javafx.scene.control.Label;
import servicios.Playlist;

/**
 *
 * @author alanc
 */
public class LabelPlaylist extends Label{
    private Playlist playlist;

    public LabelPlaylist(String text) {
        super(text);
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
    
}
