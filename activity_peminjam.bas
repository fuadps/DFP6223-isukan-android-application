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
	Dim cursor2 As Cursor
	Private LVdb As ListView
	Private btnLulus As Button
	
	Private ID As String
	Private status As String
	Private btnPadam As Button
	Private ImageView2 As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutpeminjam")
	LVdb.Clear 'need to clear the list
	Activity.Title = "Administrator Section"
	
	'initiliaze with db
	If File.Exists(File.DirInternal,"dbsukan.db") = False Then
	File.Copy(File.DirAssets,"dbsukan.db",File.DirInternal,"dbsukan.db")
	End If
	If SQL1.IsInitialized = False Then
	SQL1.Initialize(File.DirInternal, "dbsukan.db", False)
	End If
	DBload
	
	
End Sub

Sub DBload
	LVdb.Clear'need to clear the list
	cursor2 = SQL1.ExecQuery("SELECT * FROM tblPeminjam")
	For i = 0 To cursor2.RowCount - 1
		cursor2.Position = i
		LVdb.AddSingleLine(cursor2.GetString("ID")& ". "&cursor2.GetString("name")& " | " & cursor2.GetString("no_matrik")& "|"&cursor2.GetString("kelas")& " | " & cursor2.GetString("barang")& " | " & cursor2.GetString("tarikh") & " | " & cursor2.GetString("status"))
		LVdb.SingleLineLayout.ItemHeight = 40
		LVdb.SingleLineLayout.Label.TextSize = 12

	Next
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Sub LVdb_ItemClick (Position As Int, Value As Object)
	'user click list n get the id.ez algo
	Dim ID As String
	ID = Position
	'ID = ID + 1
	
End Sub

Sub btnLulus_Click
	status = "Diluluskan"
	SQL1.ExecNonQuery("UPDATE tblPeminjam set status ='"&status&"' WHERE ID = " &ID )
	DBload
End Sub

Sub btnxLulus_Click
	status = "Tidak Diluluskan"
	SQL1.ExecNonQuery("UPDATE tblPeminjam set status ='"&status&"' WHERE ID = " &ID )
	DBload
End Sub

Sub btnPadam_Click
	SQL1.ExecNonQuery("DELETE FROM tblPeminjam where ID = '" & ID & "' ")
	DBload
End Sub

Sub ImageView2_Click
	StartActivity(Main)
End Sub