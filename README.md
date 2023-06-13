# AppDynamics Dashboard Data Tool

To help generate external data for external charting tools

You will need the deployment, and jar files that includes, to execute:

	> java -jar CustomDashboardTool.jar -h

	usage: DashControl [-h] [-v] [-c ./config.properties] [-a Application] [-b Baseline] [-m Metric] [-d Days] [-o Output Format]
					   [--debug Verbose logging level]

	Export Dashboard Source Data from AppDynamics for a given metric.

	named arguments:
	  -h, --help             show this help message and exit
	  -v, --version
	  -c, --config ./config.properties
							 Use this specific config file. (default: config.properties)
	  -a, --application Application
							 Metric is in this specific application
	  -b, --baseline Baseline
							 Only manage a specific data type: {"Default", "Weekly", "Daily", "Monthly"} (default: Default)
	  -m, --metric Metric    Metric name to extract
	  -d, --days Days        Numbers of days to extract (default: 7)
	  -o, --output Output Format
							 Output Formats: {"XML", "JSON", "CSV", "BRAIL"} (default: XML)
	  --debug Verbose logging level
							 Print debug level logging during run: {"WARN", "INFO", "DEBUG", "TRACE"} (default: INFO)
