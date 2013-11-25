package utils;

public class positioningUtil {
	private float w;
	private float h;

	public positioningUtil(float w, float h){
		this.w = w;
		this.h = h;
	}
	
	public float xUnite(float x)
	{
	       return x*w/480f;
	}
	
	public float yUnite(float y)
	{
	       return y*h/320f;
	}
}
