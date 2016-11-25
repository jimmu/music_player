rm -f /tmp/ripcd.lock
rm -f /tmp/cdevent
# Start waiting for cds to rip
/home/pi/waitforcd.sh &

# Start the music player
cd /home/pi/music_player
/usr/bin/git pull
cd webServer
nohup /usr/bin/mvn package exec:java &
