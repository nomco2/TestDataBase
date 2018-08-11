package arabiannight.tistory.com.activity;

public class Button_data_class {


    public int _id;
    public String btn_name;
    public float x_location;
    public float y_location;
    public String coding_contents;


    public Button_data_class(int _id , String btn_name , String coding_contents, float x_location, float y_location){
        this._id = _id;
        this.btn_name = btn_name;
        this.x_location = x_location;
        this.y_location= y_location;
        this.coding_contents = coding_contents;
    }



}
