package com.sj.volleyexample;

import com.adnroid.volleyapi.image.ImageCacheManager;
import com.android.entity.BaiKe;
import com.android.entity.Weatherinfo;
import com.android.entity.Weatherinfo.RequestData;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volleyapi.RequestManager;
import com.android.volleyapi.request.BaikeRequest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private ImageView img_volley;
	private NetworkImageView network_image_view;
	private ImageView img_volley_bitmap;
	
	private TextView tv_result_show;
	
	private final String img_url = "http://g.hiphotos.baidu.com/image/pic/item/203fb80e7bec54e730a45785bb389b504ec26ae4.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		initLayout();
	}
	
	private void initLayout(){
		
		this.findViewById(R.id.volley_get_string).setOnClickListener((OnClickListener) this);
		this.findViewById(R.id.volley_get_params).setOnClickListener((OnClickListener) this);
		this.findViewById(R.id.volley_get_url).setOnClickListener((OnClickListener) this);
		this.findViewById(R.id.volley_get_img).setOnClickListener((OnClickListener) this);
		this.findViewById(R.id.volley_get_img_bitmap).setOnClickListener((OnClickListener) this);
		
		tv_result_show = (TextView) this.findViewById(R.id.tv_result_show);
		
		img_volley = (ImageView)this.findViewById(R.id.img_volley);
		img_volley_bitmap = (ImageView)this.findViewById(R.id.img_volley_bitmap);
		network_image_view = (NetworkImageView)this.findViewById(R.id.network_image_view);
		//network_image_view.setDefaultImageResId(R.drawable.ic_launcher);  
		//network_image_view.setErrorImageResId(R.drawable.ic_launcher);  
		network_image_view.setImageUrl(img_url, ImageCacheManager.getInstance().getImageLoader());
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.volley_get_string:
				BaikeRequest.getInstance().postpostWeatherString(new Listener<String>() {
					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
						tv_result_show.setText("String:"+response);
					}	
				}, 
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(MainActivity.this, "获取视频信息失败", Toast.LENGTH_SHORT).show();
					}
				});
			break;
		case R.id.volley_get_params:
				BaikeRequest.getInstance().postpostWeatherParams(new Listener<BaiKe>() {
					@Override
					public void onResponse(BaiKe arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "SUC"+arg0.getWapUrl(), Toast.LENGTH_SHORT).show();
						tv_result_show.setText("Gson params:"+arg0.getWapUrl());
					}	
				}, 
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(MainActivity.this, "获取视频信息失败", Toast.LENGTH_SHORT).show();
					}
				}, "关键词");
				
			break;
		case R.id.volley_get_url:
				BaikeRequest.getInstance().postWeatherinfo(responseListener(), errorListener());
			break;
		case R.id.volley_get_img:
				//方法1
				ImageListener listener = ImageLoader.getImageListener(img_volley,R.drawable.ic_launcher, R.drawable.ic_launcher);
				ImageCacheManager.getInstance().getImageLoader().get(img_url, listener);
				
				//方法2
				/*
				ImageRequest imgRequest=new ImageRequest(img_url, new Response.Listener<Bitmap>() {
		            @Override
		            public void onResponse(Bitmap arg0) {
		                // TODO Auto-generated method stub
		            	img_volley.setImageBitmap(arg0);
		            }
		        }, 300, 200, Config.ARGB_8888, new ErrorListener(){
		            @Override
		            public void onErrorResponse(VolleyError arg0) {
		                // TODO Auto-generated method stub

		            }           
		        });
				RequestManager.getRequestQueue().add(imgRequest);
		        */
			break;
		case R.id.volley_get_img_bitmap:
			BitmapDrawable bitmapDrawable = (BitmapDrawable) (img_volley).getDrawable();
			if(bitmapDrawable != null){
				Bitmap bitmap = bitmapDrawable.getBitmap();
				if(bitmap!= null){
					img_volley_bitmap.setImageBitmap(bitmap);
				}
			}else{
				Toast.makeText(this, "无图片对象", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
	
	
	/**
     * 获取数据成功回调
     * @return
     */
    private Response.Listener<Weatherinfo.RequestData> responseListener() {

        return new Response.Listener<Weatherinfo.RequestData>() {
			@Override
			public void onResponse(RequestData requestData) {
				Weatherinfo weatherinfo = requestData.weatherinfo;
				tv_result_show.setText("Gson url:"+weatherinfo.getCity());
				
			}
      
        };
    }

    /**
     * 获取数据错误回调
     * @return
     */
    protected Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            	Toast.makeText(MainActivity.this, "Ero", 3000).show();
            }
        };
    }

}
