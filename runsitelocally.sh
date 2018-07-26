#!/bin/bash

sudo apt install ruby-dev libxml2-dev zlib1g-dev

sudo gem install bundler
bundle install
bundle update

bundle exec jekyll serve
