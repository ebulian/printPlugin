/**
 *
 * Phonegap print plugin for Android
 * patadejaguar@gmail.com 2012
 *
 */
var LocalPrint = function() {
};

LocalPrint.prototype.print = function(content, success, fail) {
	return cordova.exec(function(args) {
		success(args);
	}, function(args) {
		fail(args);
	}, 'LocalPrint', '', [ content ]);
};

if (!window.plugins) {
	window.plugins = {};
}
if (!window.print) {
}
function printPage(src) {
	var data = (typeof src == "undefined") ? document.body.innerHTML : document
			.getElementById(src).innerHTML;
	window
			.requestFileSystem(
					LocalFileSystem.PERSISTENT,
					0,
					function(fileSystem) {
						var d = new Date();
						var fil = "tmp-" + (d.getTime()) + ".html";
						fileSystem.root
								.getFile(
										fil,
										{
											create : true
										},
										function(fileEntry) {
											var mFullPath = fileEntry.fullPath;
											fileEntry
													.createWriter(
															function(file) {
																var txt = "<!DOCTYPE html><html><head><meta charset=\"utf-8\" /></head><body>"
																		+ data
																		+ "</body></html>";
																file.write(txt);
																file.onwriteend = function(
																		evt) {
																	//console.log("write success");
																	var p = new LocalPrint();
																	//console.log(mFullPath);
																	p
																			.print(
																					{
																						fileURI : mFullPath,
																						mimeType : "text/html"
																					},
																					function() {
																					},
																					function() {
																						alert("Android Print: Error!");
																					});
																};
															}, function() {
															});
										}, function() {
										});
					}, function() {
					});
}