#!/usr/bin/env bash

# Update installed packages
sudo apt-get -y update 
sudo apt-get -y upgrade
sudo apt-get -y install curl tree vim-nox
# Create and start a server
sudo chown -R vagrant.vagrant /opt/IBM
/opt/IBM/wlp/bin/server create defaultServer
cp /vagrant/vmfiles/server.xml /opt/IBM/wlp/usr/servers/defaultServer/server.xml
sudo /opt/IBM/wlp/bin/server start defaultServer
