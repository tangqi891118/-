package file.tangqi.com.file;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
private Button leave;
private Button exit;
private EditText name;
    private static final int PERMISSON_REQUESTCODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        leave=(Button)findViewById(R.id.leave);
        exit=(Button)findViewById(R.id.exit);
        name=(EditText)findViewById(R.id.name);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length()!=0) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ApplyActivity.class);
                    intent.putExtra("name", name.getText().toString());
                    startActivity(intent);
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog isExit = new AlertDialog.Builder(MainActivity.this).create();
                isExit.setTitle("确定退出？");
                isExit.setMessage("确定要退出吗？");
                isExit.setIcon(R.mipmap.aiapps_empty_icon_error);
                isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                isExit.show();
            }
        });
          }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN){
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            isExit.setTitle("确定退出？");
            isExit.setMessage("确定要退出吗？");
            isExit.setIcon(R.mipmap.aiapps_empty_icon_error);
            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            isExit.show();
        }
        return false;
    }

}
