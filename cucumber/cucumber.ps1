$shell = new-object -com shell.application
$zip = $shell.NameSpace(“C:\Users\CPU8\Documents\Github\suppository\cucumber\implementation\cs374_anon.zip”)
foreach($item in $zip.items())
{
$shell.Namespace(“C:\Users\CPU8\Documents\Github\suppository\cucumber\implementation\”).copyhere($item)
}