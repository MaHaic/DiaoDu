package cc.c3q.algo.evolve.fms.web.ctrl;

public class BaseRet
{
	public boolean state;
	public String msg;
	public Object data;
	
	public BaseRet() {super();}
	public BaseRet(boolean state, String msg, Object data)
	{
		super();
		this.state = state;
		this.msg = msg;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "BaseRet [state=" + state + ", msg=" + msg + ", data=" + data + "]";
	}
	
	public static BaseRet err()
	{
		return err(null);
	}
	public static BaseRet err(String msg)
	{
		return v(false, msg, null);
	}
	
	public static BaseRet ok()
	{
		return ok(null);
	}
	public static BaseRet ok(Object data)
	{
		return v(true, null, data);
	}
	
	public static BaseRet v(boolean state, String msg, Object data)
	{
		return new BaseRet(state, msg, data);
	}
}
