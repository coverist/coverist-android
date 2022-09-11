# Coverist Android

[Coverist](https://github.com/coverist/coverist) is a platform for document creation, management, and publishing.

It offers a library of tools with which you can create documents with rich, contextual features, from nearly any device and any platform and with native UI support.

## How it works

| Type of document | UI | Function | Access | Detail
|------------| | | |----|----------|-------|
| Fabric Editor | A touch UI to edit text and cover art and.... | `setCover` | `setCoverArt` | |
[Add Cover](https://github.com/coverist/add-covers...)       | | `makeNewCover` | `makeNewCoverArt` |
| Publish to Cloud | Publish a document to the Cloud | `publish` | `cloudPublish` | 
| Download from Cloud | Download a document from the Cloud | `download` | `cloudDownload` | `downloadPdf` |
| Import a document | Import a file of a document into the app | `import` | `importPdf` |
| Delete a document | Delete a document in the app | `delete` | `cloudDelete` |
| Load them | Load a group of documents | `loadDocuments` | `loadPdf` | `loadPdfFiles` | |

### How to use a document

| File  | Description   | Access  | Details |
|----------|------|-----|----------|
| `build-r.id` | Pseudo | `open`   | Private |
| `resolution.id` | Pseudo | `open`   | Private |
| `Sobre.id` | Pseudo | `open`   | Private |
| `provider.id` | Pseudo | `open`   | Private |
| `version.id` | Pseudo | `open`   | Public |
| `resources.id` | Pseudo | `open`   | Public |
* `pdfMenu`   | A menu of options to change compression |
| `database`   | A database of information about your...... | `newBook` | `copyBook` | `copyCover` |
| `export`    | A file to export a book into...... | `export` | `exportPdf` | `importBook` |
* `importPdf` | A bundle of a document with information about it | `importPdfFiles` |

### How it works

![]( https://github.com/coverist/coverist-android/blob/master/img/next.png )
### How to use a feature

| Type | Description   | Access  | Details |
|------|--|-----|-------- |
| `insertCoverArt` | Insert a cover| `getImage` | 
| `download`     | Pseudo | `open`   | Private |
| `downloadPdf` | Pseudo | `open`   | Public |
| `exportCoverArt`, `exportPdf` | Download a cover| `downloadPdf` | 
| `export`       | Pseudo | `open`   | Private |
| `exportPdfFiles` | Pseudo | `open`   | Public |
| `importCoverArt` | Import a cover| `importPdfAndCover` |
| `importCoverFiles` | Import a bundle of a document with information about it | `importPdfFiles` |

### How it works

![]( https://github.com/coverist/coverist-android/blob/master/img/prev.png )
### How to use a plugin

| Plugin | Name | Description |
|-------------|-----|-------|
| `cover-icons` | Add book cover icons to the app | `cover-icon-items` |
| `cover-menu