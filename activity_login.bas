Type=Activity
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private txtuname As EditText
	Private txtpwd As EditText
	Private btnLogin As Button
	Private ImageView2 As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutlogin")
	Activity.Title = "i-Sukan Login"

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub btnLogin_Click
	
	'using hard code instead of comparing data from db 
	If txtuname.Text == "" Or txtpwd.Text == "" Then
		ToastMessageShow("Please insert your username and password in the textfield",False)
	Else If txtuname.Text <> "admin" Or txtpwd.Text <> "admin123" Then
			ToastMessageShow("Please insert correct username and password in the textfield",False)
	Else If txtuname.Text == "admin" And txtpwd.Text == "admin123" Then
		StartActivity(activity_peminjam)
	Else
		ToastMessageShow("Something Wrong!",False)
	End If
End Sub

Sub ImageView2_Click
	StartActivity(Main)
End Sub