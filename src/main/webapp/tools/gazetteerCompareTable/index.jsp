<!DOCTYPE html>
<html>
	<head>
		<title>i3.gazetteerCompareTable</title>
		<link rel="stylesheet" type="text/css" href="../css/default.css">
		<!-- jQuery -->
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<!-- https://github.com/joequery/Stupid-Table-Plugin -->
		<script src="../vendor/stupid-table-plugin/stupidtable.js"></script>
		<!-- Config File -->
		<script src="../Config.js"></script>
		
		<style>
			table {
				border-collapse: collapse;
				margin: 1em auto;
				width: 90%;
				vertical-align: middle;
			}
			th, td {
				padding: 5px 10px;
				border: 1px solid #999;
				font-size: 12px;
				height: 25px;
			}
			th {
				background-color: #eee;
				font-weight: bold;
				font-size: 14px;
				vertical-align: middle;
			}
			th[data-sort]{
				cursor:pointer;
			}

			/* just some random additional styles for a more real-world situation */
			#msg {
				text-align: center;
				margin-top: 20px;
				margin-bottom: 20px;
				font-size: 20px;
				font-weight: normal;
			}
			#arrow {
				vertical-align: middle;
			}
			td.uri {
				color: #666;
				text-decoration: underline;
			}
			/* zebra-striping seems to really slow down Opera sometimes */
			tr:nth-child(even) > td {
				background-color: #f9f9f7;
			}
			tr:nth-child(odd) > td {
				background-color: #ffffff;
			}
			.disabled {
				opacity: 0.5;
			}
		</style>
		
		<script>
			var url = Config.servletURL;
			var pointsURL = url + "getPlacesAndDistances";
			var LAT = null;
			var LON = null;
			var NAME = null;
			var SORT = null;
			LAT = "<%=request.getParameter("lat")%>";
			LON = "<%=request.getParameter("lon")%>";
			NAME = "<%=request.getParameter("name")%>";
			SORT = "<%=request.getParameter("sort")%>";
			LAT = Math.round(LAT * 100000) / 100000;
			LON = Math.round(LON * 100000) / 100000;
			
			$(document).ready(function () {
				// init stupidtable
				var table = $("#data").stupidtable();
				$("#msg").css("color", "green");
				$("#msg").text("loading...");
				table.on("beforetablesort", function (event, data) {
					//$("#msg").text("sorting...");
					$("#data").addClass("disabled");
				});
				table.on("aftertablesort", function (event, data) {
					// reset loading message
					$("#data").removeClass("disabled");
					// set arrow
					var th = $(this).find("th");
					th.find(".arrow").remove();
					var dir = $.fn.stupidtable.dir;
					//var arrow = data.direction === dir.ASC ? "&uarr;" : "&darr;";
					var arrow = data.direction === dir.ASC ? "<img src='../img/asc.png'>" : "<img src='../img/desc.png'>";
					th.eq(data.column).append('<span class="arrow">&nbsp;&nbsp;' + arrow + '</span>');
				});
				// loda data
				$.ajax({
					type: 'GET',
					url: pointsURL,
					data: {
						lat: LAT,
						lon: LON,
						name: NAME
					},
					error: function (jqXHR, textStatus, errorThrown) {
						$("#msg").css("color", "red");
						$("#msg").text(errorThrown);
					},
					success: function (response) {
						try {
							response = JSON.parse(response);
						} catch (e) {
						}
						// reset message span
						var header = response.place.name+" ("+response.place.lat+";"+response.place.lon+")";
						// fill table
						var trHTML = '';
						var features = response.features;
						if (features) {
							for (var i = 2; i < features.length; i++) {
								trHTML += '<tr><td>' + features[i].properties.name + '</td><td>' + features[i].properties.provenance + '</td><td class="uri"><a href="'+features[i].properties.uri+'" target="_blank">'+features[i].properties.uri+'</a></td><td>' + features[i].properties.distance + '</td><td>' + features[i].properties.similarity.levenshtein + '</td><td>' + features[i].properties.similarity.normalizedlevenshtein + '</td><td>' + features[i].properties.similarity.dameraulevenshtein + '</td><td>' + features[i].properties.similarity.jarowinkler + '</td></tr>';
							}
							$('#data').append(trHTML);
							// sort first column
							if (SORT==="distance") {
								var th_to_sort = table.find("thead th").eq(3);
								th_to_sort.stupidsort('asc');
							} else if (SORT==="string") {
								var th_to_sort = table.find("thead th").eq(5);
								th_to_sort.stupidsort('asc');
							} else {
								var th_to_sort = table.find("thead th").eq(0);
								th_to_sort.stupidsort('asc');
							}
							// set header
							$("#msg").css("color", "black");
							$("#msg").css("font-weight", "bold");
							$("#msg").html(header);
						} else {
							$("#msg").css("color", "red");
							$("#msg").text("sorry, request error...");
							$("#table").html("");
						}
					}
				});

			});
		</script>
	</head>

	<body>
		<div id="msg">&nbsp;</div>
		<div id="table">
			<table id="data">
				<thead>
					<tr>
						<th data-sort="string">name</th>
						<th data-sort="string">provenance</th>
						<th class="uri">URI</th>
						<th data-sort="float" data-sort-default="arc">distance</th>
						<th data-sort="int">levenshtein</th>
						<th data-sort="float">normalized levenshtein</th>
						<th data-sort="int">damerau-levenshtein</th>
						<th data-sort="float">jaro winkler</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
</html>
