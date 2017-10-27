package base_http.packet;

import util.EncryptUtil;
import util.Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseRequestContent {
	public final static String ContentType="content-type";
	public final static String ContentType_Json="application/json";
	private String url;
	private Map<String, Object> contentMap;
	private Map<String, String> headerMap;
	public BaseRequestContent(String url) {
		this(url, null, null);
		initHeaderMap();
		initContentMap();
	}

	public BaseRequestContent(String url, Map<String, Object>contentMap ) {
		this(url, null, contentMap);
		initHeaderMap();
	}

	public BaseRequestContent(String url, Map<String, String>headerMap, Map<String, Object> contentMap) {
		this.url=url;
		this.contentMap=contentMap;
		this.headerMap=headerMap;
	}


	private void initHeaderMap(){
		if(headerMap==null){
			headerMap=new ConcurrentHashMap<>();
//			headerMap.put(ContentType, ContentType_Json);
		}
	}

	private void initContentMap(){
		if(contentMap==null){
		    contentMap=new ConcurrentHashMap<>();
		}
	}

	public final Map<String, String> getHeader(){
		return headerMap;
	}

	public final Map<String, Object> getContent(){
		return contentMap;
	}

	public final String getUrl(){
		return url;
	}

	public void setUrl(String url){
		this.url=url;
	}
	public void putParamComm(){
		contentMap.put("comm", Util.getSimpleParams());
	}

	public void putParamSignkey(int uid, String token){
		long time = System.currentTimeMillis() / 1000;
		contentMap.put("time", String.valueOf(time));
		contentMap.put("signkey", EncryptUtil.getSignKey(uid, time, token));
	}

	@Override
	public String toString() {
		return "BaseRequestContent{" +
				"url='" + url + '\'' +
				", contentMap=" + contentMap +
				", headerMap=" + headerMap +
				'}';
	}
	private NetworkFile postFile;
	protected void setFile(NetworkFile postFile){
		this.postFile=postFile;
	}
	public NetworkFile getFile() {
		return postFile;
	}
}
