#!/bin/sh
PID=$(cat target/ReadingPlan365.pid)
kill -9 $PID
