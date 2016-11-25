#!/bin/sh
while [ true ]
do
  if [ -e /tmp/cdevent ]; then
    rm /tmp/cdevent
    /home/pi/ripcd.sh
  fi
  sleep 5;
done
