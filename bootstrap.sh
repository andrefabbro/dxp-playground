#!/bin/bash

COMMAND="$1"
ENV="$2"

## Clean and create a new DB instance
init_db() {
	
	echo "=============================="
	echo "Creating database"
	echo "=============================="
	docker stop dxp-playground || true
	docker rm dxp-playground || true
	docker run --name dxp-playground -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=lportal --publish 3306:3306 -d mysql:5.6 --character-set-server=utf8 --collation-server=utf8_general_ci --lower-case-table-names=0
}

##
## Setup a new Liferay EE Bundle and apply the patch
## Make sure that you have the last patch in your user home directory
## 
init_bundle() {
	echo "=============================="
	echo "Creating Bundle"
	echo "=============================="
	
	# Set the common variables
	APP_SERVER_PATH=./bundles/tomcat-8.0.32
	LIFERAY_BUNDLE_PATH=$APP_SERVER_PATH/webapps/ROOT
	WEB_XML_PATH=$LIFERAY_BUNDLE_PATH/WEB-INF
	FIX_PACK_FILE=~/.liferay/fix-packs/liferay-fix-pack-de-48-7010.zip
	
	# Re-creates the base bundle package
	rm -rf bundles
	
	# Init the bundle from the base package
	blade gw initBundle -x downloadBundle
	
	# Removes the old version of LCS plugin
	rm -rf ./bundles/osgi/marketplace/Liferay\ Connected\ Services\ Client.lpkg
	
	# Copy and Install Liferay Patch
	cp $FIX_PACK_FILE bundles/patching-tool/patches
	./bundles/patching-tool/patching-tool.sh install
	
	echo "=============================="
	echo "Bundle created, you can start local using blade server start"
	echo "=============================="
}

## Init Bundle Environment
init() {
	init_db
	init_bundle
}

build_package() {
	init_bundle
}

case "${COMMAND}" in
	init ) init
        exit 0
        ;;
	bundle ) build_package
        exit 0
        ;;
  *)
    echo $"Usage: $0 {init, bundle}"
      exit 1
esac
exit 0