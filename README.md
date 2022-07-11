# FoodApp

### Architecture

FoodApp uses MVVM (Model / View / ViewModel) architecture.

![mvvm](https://user-images.githubusercontent.com/32228545/178334502-709ae435-86d0-445e-a495-9234110ccae7.png)

We use this architecture to help separate concerns. Instead of having a single activity with 3000 lines of code containing ALL app logic, we separate concerns into layers with distinct functionality. This allows the code to be much easier to read, comprehend, maintain, and test.

Layers:

- **Model**: The data layer of the app. This usually contains your data models (e.g. FoodishResponse) which hold data. These data models may also contain logic which operates on the data.

- **View**: The UI of the app. Typically consists of activities/fragments and their associated layout XML files. We try to keep this layer free of complex logic and instead have it focus on simply observing data from the ViewModel and setting it on the appropriate views. It is also where we register event handlers such as a click listener for a button.

- **ViewModel**: Prepares data for the view. It can for example kick off the chain of events to fetch a piece of data from a remote API. Upon receiving data, it can run logic to further prepare the data for the view (for example, maybe it filters a list of data according to some filters that the user has set). Once prepared, it will set the data on one or more observable fields which the View layer is actively observing. When the data is set on these observable fields, the View layer is notified and updates its views accordingly.
