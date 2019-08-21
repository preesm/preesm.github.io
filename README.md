# preesm.github.io

This repository holds the source for Preesm website (https://preesm.github.io/).

It is built with Jekyll and few plugins (see [Gemfile](Gemfile)), and served by GitHub-pages. 
It is possible to build it locally (see [runsitelocally.sh](runsitelocally.sh) for basic guidelines). 

See [Jekyll Documentation](https://jekyllrb.com/docs/home/).

⚠️ **Note**: Rendering on Github and GitHub-Pages can differ. ⚠️ 


## Adding Content

Note: Editing/adding content to the site requires proper permissions in the [Preesm Team](https://github.com/preesm). If you do not have the permission to push, you can submit new content or modifications using pull requests.

### Site Structure

* Top and left navigation menus are described in [`_data/navigation.yml`](_data/navigation.yml). They activate depending on the category the reader is in (configured in [`_config.yml`](_config.yml));
* Table of Content on the right is automaticaly generated when `toc: true` is used. It is based in the titles found in the content file;
* The Twitter left panel on Home, About and Publications pages is a hack (see [`./_includes/sidebar.html`](/_includes/sidebar.html))
* Home page is automatically generated (see below);
* All the other content is defined in `.md` files:
  * Home posts are located in `./_posts`;
  * About and Publications pages are defined in `./[_about,_publis]/index.md`;
  * Index pages for Documentation, Tuorials and 'Get Preesm' are defined in `./[_docs,_tutos,_get]/00-index.md`. The number is used to have the next button in the bottom to work properly.
  * Other pages are found under `./[_docs,_tutos,_get]`.

### Content Format

The content of the site is written in [kramdown](https://kramdown.gettalong.org/index.html) (Markdown like) with meta data interpreted by Jekyll.

The meta data take place in a header delimited by dashes:
```
---
title:  "Page title"
permalink: /perma/link/to/access/the/page
toc: true
...
other meta data
...
---
```

The `published: false` meta data can be used to have the markdown file present but forbid its access.

The file name is also used as a meta data for ordering:
* the posts on the home page (see below);
* the different pages of a category when use Next / Previous buttons in the bottom of a page.

### Editing

To edit existing content, either clone the repository, do the changes, then push; or edit directly via the GitHub interface (note that rendering differs between GitHub and GitHub-Pages).

Note that the file **/_docs/01-workflow-tasks-ref.md** is automaticaly generated and published during the release of Preesm.

### New Post in Home

Posts in the Home page are automatically read from files in [`./_posts`](_posts/). The file name of the post needs to start with the date (YYYY-MM-DD) followed by a unique identifier. 2 posts with different dates but same identifier will mess up the Next / Previous links. Title will be read from the meta data.

If a post is edited, add/edit the `last_modified_at: 2018-08-21T10:10:00-00:00` meta data.

### New Tutorial / Documentation

* Create a new file in the appropriate folder. Possibly rename other files to insert it in between existing tutorials;
    * Set its permalink properly
* Add a reference to this permalink in the proper navigation menu ([_data/navigation.yml](_data/navigation.yml));

### Adding Assets

Pictures, Zip files, etc. should be added in the `./assets/` folder, under proper folder structure.

### New Categoty

_Undocumented yet._

(requires adding new folder, extra config in `_config.yml`, new menu entries, etc.)
