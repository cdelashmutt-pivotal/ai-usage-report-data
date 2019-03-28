CREATE TABLE IF NOT EXISTS `org_app_usage` (
	`id` int(11) NOT NULL auto_increment,
	`period_start` datetime,
	`period_end` datetime,
	`platform` varchar(255),
	`organization_guid` varchar(255),
	`organization_name` varchar(255),
	`space_guid` varchar(255),
	`space_name` varchar(255),
	`app_guid` varchar(255),
	`app_name` varchar(255),
	`instance_count` smallint,
	`memory_in_mb_per_instance` int,
	`duration_in_seconds` int,
	PRIMARY KEY( `id` )
);