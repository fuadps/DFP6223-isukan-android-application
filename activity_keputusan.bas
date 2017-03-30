Type=Activity
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

'Activity module
Sub Process_Globals
	Dim SQL1 As SQL
End Sub

Sub Globals
	
	Dim SV As ScrollView
	Dim Header As Panel
	Dim Table As Panel
	Dim NumberOfColumns, RowHeight, ColumnWidth As Int
	Dim HeaderColor, TableColor, FontColor, HeaderFontColor As Int
	Dim FontSize As Float
	Type RowCol (Row As Int, Col As Int)
	Dim Alignment As Int
	Dim SelectedRow As Int
	Dim SelectedRowColor As Int
	
	'Table settings
	HeaderColor = Colors.Gray
	NumberOfColumns = 2
	RowHeight = 30dip
	TableColor = Colors.White
	FontColor = Colors.Black
	HeaderFontColor = Colors.White
	FontSize = 14
	Alignment = Gravity.CENTER 'change to Gravity.LEFT or Gravity.RIGHT for other alignments.
	SelectedRowColor = Colors.Blue
	
	Dim cursor2 As Cursor
	Private ImageView2 As ImageView
End Sub


Sub Activity_Create(FirstTime As Boolean)
	
	Activity.LoadLayout("layoutkeputusan")
	Activity.Title = "Keputusan Permohonan"
	
	'initiliaze with db
	If File.Exists(File.DirInternal,"dbsukan.db") = False Then
	File.Copy(File.DirAssets,"dbsukan.db",File.DirInternal,"dbsukan.db")
	End If
	If SQL1.IsInitialized = False Then
	SQL1.Initialize(File.DirInternal, "dbsukan.db", False)
	End If
	
	cursor2 = SQL1.ExecQuery("SELECT * FROM tblPeminjam")
	Dim rowlist As Int
	rowlist = cursor2.RowCount
	
	SV.Initialize(0)
	Table = SV.Panel
	Table.Color = TableColor
	Activity.AddView(SV, 5%x, 10%y, 90%x, 80%y)
	ColumnWidth = SV.Width / NumberOfColumns
	SelectedRow = -1
	
	'add header
	SetHeader(Array As String("Nama", "Status"))
	
	For i = 0 To rowlist - 1
		cursor2.Position = i
		AddRow(Array As String(cursor2.GetString("name"), cursor2.GetString("status")))
	Next
End Sub



Sub SelectRow(Row As Int)
	'remove the color of previously selected row
	If SelectedRow > -1 Then
		For col = 0 To NumberOfColumns - 1
			GetView(SelectedRow, col).Color = Colors.Transparent
		Next
	End If
	SelectedRow = Row
	For col = 0 To NumberOfColumns - 1
		GetView(Row, col).Color = SelectedRowColor
	Next
End Sub

Sub GetView(Row As Int, Col As Int) As Label
	Dim l As Label
	l = Table.GetView(Row * NumberOfColumns + Col)
	Return l
End Sub

Sub AddRow(Values() As String)
	If Values.Length <> NumberOfColumns Then
		Log("Wrong number of values.")
		Return
	End If
	Dim lastRow As Int
	lastRow = NumberOfRows
	For i = 0 To NumberOfColumns - 1
		Dim l As Label
		l.Initialize("cell")
		l.Text = Values(i)
		l.Gravity = Alignment
		l.TextSize = FontSize
		l.TextColor = FontColor
		Dim rc As RowCol
		rc.Initialize
		rc.Col = i
		rc.Row = lastRow
		l.Tag = rc
		Table.AddView(l, ColumnWidth * i, RowHeight * lastRow, ColumnWidth, RowHeight)
	Next
	Table.Height = NumberOfRows * RowHeight
End Sub

Sub SetHeader(Values() As String)
	If Header.IsInitialized Then Return 'should only be called once
	Header.Initialize("")
	For i = 0 To NumberOfColumns - 1
		Dim l As Label
		l.Initialize("header")
		l.Text = Values(i)
		l.Gravity = Gravity.CENTER
		l.TextSize = FontSize
		l.Color = HeaderColor
		l.TextColor = HeaderFontColor
		l.Tag = i
		Header.AddView(l, ColumnWidth * i, 0, ColumnWidth, RowHeight)
	Next
	Activity.AddView(Header, SV.Left, SV.Top - RowHeight, SV.Width, RowHeight)
End Sub

Sub NumberOfRows As Int
	Return Table.NumberOfViews / NumberOfColumns
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub ImageView2_Click
	StartActivity(Main)
End Sub