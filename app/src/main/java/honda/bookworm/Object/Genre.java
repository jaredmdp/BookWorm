package honda.bookworm.Object;

public enum Genre {
    Fiction,
    NonFiction,
    SciFi,
    Romance,
    Fantasy,
    Child,
    YoungAdult,
    Adult,
    Comedy,
    Horror,
    GraphicNovel,
    Action,
    Adventure,
    Biography,
    Historical,
    Mystery,
    Survival,
    Manga;

    //creating a nicer default output format for certain genres
    @Override
    public String toString() {
        String formattedName;

        if(this == NonFiction){
            formattedName = "Non-Fiction";
        }else if(this == SciFi){
            formattedName = "Sci-Fi";
        }else if(this == YoungAdult){
            formattedName = "Young Adult";
        }else if(this == GraphicNovel){
            formattedName = "Graphic Novel";
        }else{
            formattedName = this.name();
        }

        return formattedName;
    }
}
