#!/bin/sh
while [ true ]
do
  if [ -e /tmp/cdevent ]; then
    rm /tmp/cdevent
    /home/pi/music_player/bin/ripcd.sh
  fi
  sleep 5;
done
