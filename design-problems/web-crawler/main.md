# Design a web crawler

- A web crawler is a system that crawls the internet, discovers new pages, and indexes data for later use (e.g., by a search engine).
- Crawler Use cases
  - **Search Engine Indexing**: A crawler collects web pages to create local index for search engines.
  - **Web archiving**: Process of collecting information from web to preserve data for future use.
  - **Web mining**: It helps to discover useful knowledge from the internet. For example, financial firms use crawlers to download shareholder meetings and annual reports to learn.
  - **Web monitoring**: Crawlers help to monitor copyrights and trademark infringements over the internet.

## Problem Statement

- Build a system that crawls the internet, discovers new pages, and indexes data for later use (e.g., by a search engine).
- Must handle billions of pages, ensure politeness (not overloading sites), and provide scalable + distributed crawling.

## Step 1: Understanding the problem and Design scope

- Algorithm:
  ```
  Repeat(
    Given a set of URLs, download all web pages addressed by URLs.
    Extract URLs from these web pages.
    Add new URLs to list of URLs to be downloaded. Repeat.
  )
  ```
- But the design goes beyond the simple algorithm and is highly dependent on the scale.

> Q. What is the main purpose of tge crawler? Search engine indexing, data mining or something else?
> 
> A. Search Engine Indexing

> Q. How many web pages does the crawler collects per month?
> 
> A. 1 billion pages

> Q. What content types are included? HTML only or other content types such as PDFs and images as well.
> 
> Only HTML

> Q. Shall we consider newly added or edited web pages?
> 
> A. Yes

> Q. Do we need to store HTML pages crawled from wev?
> 
> A. Yes, up to 5 years.

> Q. Do we need to store HTML pages with duplicate content?
> 
> A. Pages wirth duplicate content should be ignored.

### Characteristics for a good crawler:
  - `Scalability`: The web is very large. There are billions of web pages out there. Web
    crawling should be extremely efficient using parallelization.
  - `Robustness`: The web is full of traps. Bad HTML, unresponsive servers, crashes,
    malicious links, etc. are all common. The crawler must handle all those edge cases.
  - `Politeness`: The crawler should not make too many requests to a website within a short
    time interval.
  - `Extensibility`: The system is flexible so that minimal changes are needed to support new
    content types. For example, if we want to crawl image files in the future, we should not
    need to redesign the entire system.

### Estimations

- Assume 1 billion web pages are downloaded every month.
- QPS: 1,000,000,000 / 30 days / 24 hours / 3600 seconds = ~400 pages per second. 
- Peak QPS = 2 * QPS = 800 
- Assume the average web page size is 500k.
- 1-billion-page x 500k = 500 TB storage per month. 
• Assuming data are stored for five years, 500 TB * 12 months * 5 years = 30 PB. A 30 PB
storage is needed to store five-year content.

## Step 2: High Level Design

### Components

#### Seed URLs
- Starting point for crawl process.
- A set of initial web addresses (URLs) to begin crawling.
- Example
  - To crawl all web pages from university's website, intuitive way to select seed URLs is to use the university's domain name.
- General strategy is to divide the entire URL space into smaller ones.
- Seed URL selection is an open-ended question.
- `Analogy`: Think of seed URLs as the roots of the crawling tree, from which the crawler explores the rest of the web.

#### URL Frontier
- The component that stores URLs to be download is called the URL frontier.
- Like a FIFO queue.

#### HTML downloader / Fetcher
- The HTML downloader downloads web pages from the internet.

#### DNS resolver
- To download a web page, URL must be translated to IP address.
- HTML downloader calls the DNS resolver to get the corresponding to get the IP address.

#### Content parser
- After a web page is downloaded, it must be parsed and validated because malformed web pages could provoke problems and waste storage space.
- Extracts text, links, and metadata.
- Removes session IDs & duplicates.

#### Content Seen?
- “Content Seen?” data structure is used to eliminate data redundancy and shorten processing time.
- To compare two HTML documents, we can compare them character by character. 
- However, this method is slow and time-consuming, especially when billions of web pages are involved. 
- An efficient way to accomplish this task is to compare the hash values of the two web pages

#### Content Storage
- It is a storage system for storing HTML content.
- Choice of storage system depends on factors such as data type, data size, access frequency, life span, so on.
- Both disk and memory are used.
  - Most of tge content is store on disk because the data set is too big to fit in memory.
  - Popular content is kept in memory to reduce latency.