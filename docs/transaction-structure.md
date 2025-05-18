# Transaction Structure

## Request Structure
* Method
* URL
* Headers
    - Content Type
    - Authorization
    - X-Correlation-ID
    - User-Agent
    - Cookie
* Body

## Response Structure
* Status
* StatusText (Optional)
* Headers
    - Location (for POST) - is a URL
    - Content Type
    - Authorization
* Body
* Success
* Message
* Data
    - createdAt (for POST)
    - updatedAt (for PUT)
    - deletedAt (for DELETE)

> [!NOTE]
> Is content type even needed?
>   - Yes, because for basic data, it's json, but the streaming service is also
>     something to think about
> Authorization probably won't be implemented any time soon
