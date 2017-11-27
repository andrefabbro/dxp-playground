var files = `ls -1 /Users/andrefabbro/DEV/workspaces/eclipse-neon-general/liferay-workspace/bundles/etc/scripts`.split('\n');
var cmdnames = [];
var gogocmds = [];

files.forEach(function(file) {
	if (!file) return;
	if (file.indexOf('.js') != file.length - 3) return;

	var cmdname = file.substr(0, file.length - 3);

	cmdnames.push(cmdname);
	
	cmdexec = 'load(\'/Users/andrefabbro/DEV/workspaces/eclipse-neon-general/liferay-workspace/bundles/etc/scripts/' + file + '\')';
	gogocmds.push(cmdname + ' = { $engine eval "' + cmdexec + '" }'); 
});

var res = '';

print("\nFound these custom commands: " + cmdnames.join(' '));

gogocmds.forEach(function(cmd) {
	res += (cmd + '; ');
});

res;