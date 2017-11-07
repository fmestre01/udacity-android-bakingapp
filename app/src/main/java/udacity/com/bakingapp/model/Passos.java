package udacity.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passos implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String descricaoCurta;
    @SerializedName("description")
    @Expose
    private String descricao;
    @SerializedName("videoURL")
    @Expose
    private String urlVideo;
    @SerializedName("thumbnailURL")
    @Expose
    private String urlThumbnail;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public static Creator<Passos> getCREATOR() {
        return CREATOR;
    }

    protected Passos(Parcel in) {
        descricaoCurta = in.readString();
        descricao = in.readString();
        urlVideo = in.readString();
        urlThumbnail = in.readString();
    }

    public static final Creator<Passos> CREATOR = new Creator<Passos>() {
        @Override
        public Passos createFromParcel(Parcel in) {
            return new Passos(in);
        }

        @Override
        public Passos[] newArray(int size) {
            return new Passos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descricaoCurta);
        dest.writeString(descricao);
        dest.writeString(urlVideo);
        dest.writeString(urlThumbnail);
    }
}

