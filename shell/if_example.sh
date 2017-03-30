#!/usr/bin/env bash
T1='foo'
T2='bar'
if [[ $T1 == "$T2" ]]
then
  echo 'условие выполняется'
else
  echo 'условие не выполняется'
fi