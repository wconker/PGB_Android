package com.android.pgb.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.pgb.Bean.Imagepxh;
import com.android.pgb.R;
import com.android.pgb.Utils.Image.UploadUtil;
import com.android.pgb.Utils.ImageUpload;
import com.android.pgb.View.CBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivityUpLoadImage extends Activity {
    private GridView gridview;
    private MyAdapter adapter;
    private List<Imagepxh> list;
    private List<String> urlList;

    private Button button1;
    private File file;
    private ImageView upload_image;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_image);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onRightClick() {
                super.onRightClick();

            }

            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
        }, null);
        gridview = (GridView) findViewById(R.id.gridview);
        button1 = (Button) findViewById(R.id.upload);
        list = new ArrayList<Imagepxh>();
        urlList=new ArrayList<>();
        adapter = new MyAdapter(this, list);
        upload_image = (ImageView) this.findViewById(R.id.upload_image);
        gridview.setAdapter(adapter);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                for (int i = 0; i < list.size(); i++) {
                    file = new File(ImageUpload.getImageAbsolutePath(ActivityUpLoadImage.this, list.get(i).getImage_uri()));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO Auto-generated method stub
                            String result = UploadUtil.uploadFile(file);
                            Message msg = handler.obtainMessage(0, result);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
        handler = new Handler() {
            public void handleMessage(Message msg) {
                Log.i("response", String.valueOf(msg.obj));
                if (msg.obj.equals("SUCCESS")) {
                    Toast.makeText(ActivityUpLoadImage.this, "TT", Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;


    /***
     * 弹出框选择方式
     */
    protected void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("先择方式");
        String[] items = {"从本地选择", "拍照"};
        builder.setNegativeButton("确定", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case CHOOSE_PICTURE: //
                        Intent intent1 = new Intent();
                        intent1.addCategory(Intent.CATEGORY_OPENABLE);
                        intent1.setType("image/*");
                        intent1.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent1, 0);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        uri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);

                        break;
                }
            }
        });
        builder.create().show();
    }



    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Imagepxh> list;// this image file uri
        private ImageView image;

        public MyAdapter(Context context, List<Imagepxh> list) {
            this.context = context;
            this.list = list;
        }

        public void addList(Uri uri) {
            Imagepxh image = new Imagepxh(uri);
            list.add(image);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size() + 1;
        }


        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.gridview_item, null);
            image = (ImageView) convertView.findViewById(R.id.image);
            if (position < list.size()) {
                image.setImageURI(list.get(position).getImage_uri());
            } else if (position == list.size()) {
                image.setImageDrawable(getResources().getDrawable(
                        R.drawable.tu_plus));
            }
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChoosePicDialog();
                }
            });
            return convertView;
        }

    }

    protected static Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Toast.makeText(this, uri + "success", Toast.LENGTH_LONG).show();
            uri = data.getData();// find uri
            upload_image.setImageURI(uri);

        }

        String path;
        if (requestCode == 1) { //拍照图片
            Toast.makeText(this, uri + "成功", Toast.LENGTH_LONG).show();
            Bitmap bitmap = ImageUpload.getBitmapFromUri(uri, ActivityUpLoadImage.this);
            upload_image.setImageBitmap(bitmap);
            path =  ImageUpload.getImageAbsolutePath(this,uri);
        }


        adapter.addList(uri);// list.add();
        adapter.notifyDataSetChanged();

    }
}