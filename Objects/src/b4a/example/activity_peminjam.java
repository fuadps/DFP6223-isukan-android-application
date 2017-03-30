package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class activity_peminjam extends Activity implements B4AActivity{
	public static activity_peminjam mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.activity_peminjam");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (activity_peminjam).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.activity_peminjam");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.activity_peminjam", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (activity_peminjam) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (activity_peminjam) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return activity_peminjam.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (activity_peminjam) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (activity_peminjam) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _sql1 = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor2 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvdb = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlulus = null;
public static String _id = "";
public static String _status = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnpadam = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.activity_borang _activity_borang = null;
public b4a.example.activity_keputusan _activity_keputusan = null;
public b4a.example.activity_login _activity_login = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"layoutpeminjam\")";
mostCurrent._activity.LoadLayout("layoutpeminjam",mostCurrent.activityBA);
 //BA.debugLineNum = 28;BA.debugLine="LVdb.Clear 'need to clear the list";
mostCurrent._lvdb.Clear();
 //BA.debugLineNum = 29;BA.debugLine="Activity.Title = \"Administrator Section\"";
mostCurrent._activity.setTitle((Object)("Administrator Section"));
 //BA.debugLineNum = 32;BA.debugLine="If File.Exists(File.DirInternal,\"dbsukan.db\") = F";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"dbsukan.db")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 33;BA.debugLine="File.Copy(File.DirAssets,\"dbsukan.db\",File.DirInt";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dbsukan.db",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"dbsukan.db");
 };
 //BA.debugLineNum = 35;BA.debugLine="If SQL1.IsInitialized = False Then";
if (_sql1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 36;BA.debugLine="SQL1.Initialize(File.DirInternal, \"dbsukan.db\", F";
_sql1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"dbsukan.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 38;BA.debugLine="DBload";
_dbload();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _btnlulus_click() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub btnLulus_Click";
 //BA.debugLineNum = 73;BA.debugLine="status = \"Diluluskan\"";
mostCurrent._status = "Diluluskan";
 //BA.debugLineNum = 74;BA.debugLine="SQL1.ExecNonQuery(\"UPDATE tblPeminjam set status";
_sql1.ExecNonQuery("UPDATE tblPeminjam set status ='"+mostCurrent._status+"' WHERE ID = "+mostCurrent._id);
 //BA.debugLineNum = 75;BA.debugLine="DBload";
_dbload();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _btnpadam_click() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub btnPadam_Click";
 //BA.debugLineNum = 85;BA.debugLine="SQL1.ExecNonQuery(\"DELETE FROM tblPeminjam where";
_sql1.ExecNonQuery("DELETE FROM tblPeminjam where ID = '"+mostCurrent._id+"' ");
 //BA.debugLineNum = 86;BA.debugLine="DBload";
_dbload();
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _btnxlulus_click() throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub btnxLulus_Click";
 //BA.debugLineNum = 79;BA.debugLine="status = \"Tidak Diluluskan\"";
mostCurrent._status = "Tidak Diluluskan";
 //BA.debugLineNum = 80;BA.debugLine="SQL1.ExecNonQuery(\"UPDATE tblPeminjam set status";
_sql1.ExecNonQuery("UPDATE tblPeminjam set status ='"+mostCurrent._status+"' WHERE ID = "+mostCurrent._id);
 //BA.debugLineNum = 81;BA.debugLine="DBload";
_dbload();
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _dbload() throws Exception{
int _i = 0;
 //BA.debugLineNum = 43;BA.debugLine="Sub DBload";
 //BA.debugLineNum = 44;BA.debugLine="LVdb.Clear'need to clear the list";
mostCurrent._lvdb.Clear();
 //BA.debugLineNum = 45;BA.debugLine="cursor2 = SQL1.ExecQuery(\"SELECT * FROM tblPeminj";
mostCurrent._cursor2.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tblPeminjam")));
 //BA.debugLineNum = 46;BA.debugLine="For i = 0 To cursor2.RowCount - 1";
{
final int step3 = 1;
final int limit3 = (int) (mostCurrent._cursor2.getRowCount()-1);
for (_i = (int) (0) ; (step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3); _i = ((int)(0 + _i + step3)) ) {
 //BA.debugLineNum = 47;BA.debugLine="cursor2.Position = i";
mostCurrent._cursor2.setPosition(_i);
 //BA.debugLineNum = 48;BA.debugLine="LVdb.AddSingleLine(cursor2.GetString(\"ID\")& \". \"";
mostCurrent._lvdb.AddSingleLine(mostCurrent._cursor2.GetString("ID")+". "+mostCurrent._cursor2.GetString("name")+" | "+mostCurrent._cursor2.GetString("no_matrik")+"|"+mostCurrent._cursor2.GetString("kelas")+" | "+mostCurrent._cursor2.GetString("barang")+" | "+mostCurrent._cursor2.GetString("tarikh")+" | "+mostCurrent._cursor2.GetString("status"));
 //BA.debugLineNum = 49;BA.debugLine="LVdb.SingleLineLayout.ItemHeight = 40";
mostCurrent._lvdb.getSingleLineLayout().setItemHeight((int) (40));
 //BA.debugLineNum = 50;BA.debugLine="LVdb.SingleLineLayout.Label.TextSize = 12";
mostCurrent._lvdb.getSingleLineLayout().Label.setTextSize((float) (12));
 }
};
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim cursor2 As Cursor";
mostCurrent._cursor2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private LVdb As ListView";
mostCurrent._lvdb = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnLulus As Button";
mostCurrent._btnlulus = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private ID As String";
mostCurrent._id = "";
 //BA.debugLineNum = 20;BA.debugLine="Private status As String";
mostCurrent._status = "";
 //BA.debugLineNum = 21;BA.debugLine="Private btnPadam As Button";
mostCurrent._btnpadam = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _imageview2_click() throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub ImageView2_Click";
 //BA.debugLineNum = 90;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _lvdb_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub LVdb_ItemClick (Position As Int, Value As Obje";
 //BA.debugLineNum = 66;BA.debugLine="Dim ID As String";
mostCurrent._id = "";
 //BA.debugLineNum = 67;BA.debugLine="ID = Position";
mostCurrent._id = BA.NumberToString(_position);
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim SQL1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
