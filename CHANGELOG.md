# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.


## [2026.11.11]
- Added 35 JUnit tests for kernel, Standard, secondary, and common methods.
- Added checks for preservation of journal contents and entry order.
- Added TradeSessionReport as a direct client of the component.
- Added PracticeTradingAccount using TradeJ as part of its representation.
- Added a project README with usage instructions and a test plan.
- Added the final project reflection.
## [2026.11.10]

### Added

- Implemented TradeJ1L using an OSU Stack<Integer>.
- Added an empty constructor and the createNewRep helper method.
- Implemented all kernel and Standard methods.
- Documented the representation convention and correspondence.
- Added the Part 5 representation choice and justification.
- Implemented TradeJSecondary, which implements the TradeJ interface.
- Implemented totalProfit, winningTrades, and winRate.
- Implemented toString, equals, and hashCode.

## [2026.11.9]

### Added

- Implemented TradeJSecondary, which implements the TradeJ interface.
- Implemented totalProfit, winningTrades, and winRate.
- Implemented toString, equals, and hashCode.
- Added an assertion for the nonempty precondition of winRate.




## [2026.11.8]

### Added

- Added TradeJKernel, extending Standard<TradeJ>.
- Added the TradeJ enhanced interface, extending TradeJKernel.
- Added JavaDoc comments and contracts for every interface method.
- Added a hierarchy diagram for the component.
- Implemented a proof of concept for TradeJ in one Java file.
- Added methods to record and remove trade results.
- Added methods to calculate total profit, winning trades, and win rate.
- Added a main method demonstrating trade review and entry correction.
- Added the Part 2 design justification and implementation summary.
- Designed three components: TradeJ, StockWatchlist, and PriceHistory.
- Described kernel and secondary methods for the three designs.


## [2026.11.7]

### Added

- Designed three components: TradeJ, StockWatchlist, and PriceHistory.
- Described kernel and secondary methods for all three designs.
- Implemented a proof of concept for TradeJ in one Java file.
- Added methods to add and remove trade results.
- Added methods to calculate total profit, winning trades, and win rate.
- Added a main method to demonstrate reviewing trades and correcting an entry.
- Added the Part 2 design justification and implementation summary.
## [2026.11.5]

### Added

- Make a new PR call "proof of concept"
## [2026.11.5]

### Added

- Designed a PriceHistory component for storing stock prices and calculating basic summaries.

## [2026.11.4]

### Added

- Designed a StockWlist component for storing stock symbols and target buying prices.


## [2026.11.3]

### Added

- Designed a TradeJ component for recording trade results and calculating profit and win rate.

## [Unreleased]

- Fixed an issue where the course URLs dropped the `web-sw1` paths

## [2026.07.06]

### Added

- Added an AGENTS.md file
- Added simplified DIRECTIONS.md file

### Removed

- Removed TODO tree extension and replaced with fork

## [2026.01.07]

### Fixed

- Fixed broken links

### Removed

- Disabled AI features globally

## [2025.08.21]

### Removed

- Disabled an aspect of the coderunner extension

## [2024.12.30]

### Added

- Added table-based rubrics to all 6 parts of the project

### Updated

- Updated gitignore to exclude more files

### Fixed

- Fixed image markdown in the interfaces document

## [2024.08.07]

### Added

- Added `/bin` to `.gitignore`, so binaries are no longer committed
- Added the TODO tree extensions to `extensions.json`
- Added the `todo-tree.general.showActivityBarBadge` setting to `settings.json`
- Added the `todo-tree.tree.showCountsInTree` setting to `settings.json`
- Added the VSCode PDF extension to `extensions.json`
- Added `java.debug.settings.vmArgs` setting to enable assertions (i.e., `-ea`)
- Added information about making branches to all parts of the project
- Added information about how to update the CHANGELOG to every part of the
  project
- Added information about how to make a pull request to every part of the
  project

### Changed

- Updated `settings.json` to format document on save using `editor.formatOnSave`
  setting
- Updated `settings.json` to exclude certain files from markdown to PDF
  generation using `markdown-pdf.convertOnSaveExclude` setting
- Updated `settings.json` to use latest `java.cleanup.actions` setting
- Updated `settings.json` to automatically choose line endings using `files.eol`
  setting
- Updated `settings.json` to organize imports automatically on save using the
  `editor.codeActionsOnSave` and `source.organizeImports` settings
- Changed the component brainstorming assignment to ask a few clarifying
  questions
- Changed the component brainstorming example from `Point3D` to `NaturalNumber`
  to avoid the getter/setter trend
- Updated assignment feedback sections to include a link to a survey that
  I'll actually review
- Updated README to include step about using template repo
- Updated part 3 rubric to include a hierarchy diagram
- Updated part 6 rubric to account for overall polish

### Fixed

- Fixed issue where checkstyle paths would not work on MacOS

### Removed

- Removed `java.saveActions.organizeImports` setting from `settings.json`
- Removed references to `Point3D` completely

## [2024.01.07]

### Added

- Added a list of extensions to capture the ideal student experience
- Added PDFs to the `.gitignore`
- Added the OSU checkstyle config file
- Added the OSU formatter config file
- Added a `settings.json` file to customize the student experience
- Created a README at the root to explain how to use the template repo
- Created initial drafts of the six portfolio assessments
- Added READMEs to key folders like `test` and `lib` to explain their purpose

[unreleased]: https://github.com/jrg94/portfolio-project/compare/v2026.07.06...HEAD
[2026.07.06]: https://github.com/jrg94/portfolio-project/compare/v2026.01.07...v2026.07.06
[2026.01.07]: https://github.com/jrg94/portfolio-project/compare/v2025.08.21...v2026.01.07
[2025.08.21]: https://github.com/jrg94/portfolio-project/compare/v2024.12.30...v2025.08.21
[2024.12.30]: https://github.com/jrg94/portfolio-project/compare/v2024.08.07...v2024.12.30
[2024.08.07]: https://github.com/jrg94/portfolio-project/compare/v2024.01.07...v2024.08.07
[2024.01.07]: https://github.com/jrg94/portfolio-project/releases/tag/v2024.01.07
