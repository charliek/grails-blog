Grails Blog
-----------

This repo is really just a place for me to test out various ideas for code, build, and deployment on. Not much to see.

[![Build Status](https://drone.io/github.com/charliek/grails-blog/status.png)](https://drone.io/github.com/charliek/grails-blog/latest)

Configuration Environment Variables
===================================

Github oauth information for authentication. Visit https://github.com/settings/applications to access. These must be
overridden via environment variables or groovy config overrides.

`GITHUB_CLIENT_ID`
`GITHUB_SECRET`

Prefix to the blog service endpoint. Defaults to 'http://localhost:5678'

`CLIENT_PREFIX`

Proxy url used to proxy service calls (e.g. http://localhost:8888 for Charles). Defaults to no proxy.

`PROXY_URL`

