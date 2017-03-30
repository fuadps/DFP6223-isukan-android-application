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
	
	Dim SQL1 As SQL

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private txtNama As EditText
	Private btnSubmit As Button
	Private txtNomatrik As EditText
	Private txtKelas As EditText
	Private txtBarang As EditText
	
	Private Button1 As Button
	Dim ret As String
	Private ImageView2 As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutBorang")
	Activity.Title = "Borang Pinjaman i-Sukan"
	
	
	'initiliaze with db
	If File.Exists(File.DirInternal,"dbsukan.db") = False Then
	File.Copy(File.DirAssets,"dbsukan.db",File.DirInternal,"dbsukan.db")
	End If
	If SQL1.IsInitialized = False Then
	SQL1.Initialize(File.DirInternal, "dbsukan.db", False)
	End If
	
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub btnSubmit_Click
	SQL1.ExecNonQuery("INSERT INTO tblPeminjam ('name','no_matrik','kelas','barang','tarikh','status') VALUES('"&txtNama.Text&"','"&txtNomatrik.Text&"','"&txtKelas.Text&"','"&txtBarang.Text&"','"&ret&"','dalam proses')")
	ToastMessageShow("Borang sudah dihantar.Permohonan anda sedang diproses.",False)
	StartActivity(activity_keputusan)
End Sub

Sub Button1_Click
	Dim Dd As DateDialog
	Dd.Year = DateTime.GetYear(DateTime.Now)
	Dd.Month = DateTime.GetMonth(DateTime.Now)	
	Dd.DayOfMonth = DateTime.GetDayOfMonth(DateTime.Now)
	Dd.Show("Tarikh ingin meminjam", "Tarikh", "Yes", "", "", Null)
	ToastMessageShow("Tarikh : " & Dd.DayOfMonth & "/" & Dd.Month & "/" & Dd.Year , False)
	ret = (Dd.DayOfMonth & "/" & Dd.Month & "/" & Dd.Year)

End Sub

Sub ImageView2_Click
	StartActivity(Main)
End Sub