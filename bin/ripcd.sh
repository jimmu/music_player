#!/bin/sh
if [ ! -e /tmp/ripcd.lock ]
then
  echo "Ripping in progress" >/tmp/ripcd.lock
  echo "$(date): New CD!" >>/home/pi/ripcd.log
  abcde
  if [ $? -eq 0 ]
  then
    echo "$(date): Ripping went well" >>/home/pi/ripcd.log
  else
    echo "$(date): Ripping hit some kind of problem" >>/home/pi/ripcd.log
  fi
  mpc update
  rm /tmp/ripcd.lock
else
  echo "$(date): Detected CD, but already ripping" >>/home/pi/ripcd.log
fi
