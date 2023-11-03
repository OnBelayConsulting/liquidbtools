# This command assumes that you have a liquibase.properties file set up with
# the database URL, password, etc. configured.
liquibase generate-changelog --changelog-file=everest_extract.xml
