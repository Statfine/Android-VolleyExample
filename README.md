# VolleyExample
VolleyAPI 图片 Demo

代码附上了天气预报的接口作为例子，二次封装了请求的方法，
   
    /*
     * method 方法
     * url 链接
     * clazz 类型
     * listener 成功回调
     * errorListener 错误回调
     * headers 头部添加
     * params 参数
     */

    public GsonRequest( int method, 
    					String url,
    					Class<T> objectClass, 
    					Listener<T> listener, 
    					ErrorListener errorListener,
    					Map<String,String> header,
    					Map<String, String> params) {

		super(method, url, errorListener);
		this.mHeaders = header;
		this.mClazz = objectClass;
		this.mListener = listener;
		this.mParams = params;
	}
	以上为完整的方法，可在代码按照自己的需求做修改和添加
