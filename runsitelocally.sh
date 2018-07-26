#!/bin/bash

sudo gem install bundler
sudo bundle install
sudo bundle update

bundle exec jekyll serve
