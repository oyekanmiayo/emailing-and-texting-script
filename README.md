# emailing-and-texting-script
Java code to send emails and texts at intervals. Made to support the #EndSarsNow Campaign.

# Getting Started

## Prerequisites
* Git
* Intelli J (or similar)
* Heroku Account
* Heroku CLI installed
* Twilio Account (for SMS)
* Gmail & Gmail App Password (Read about how to create a gmail app password [here](https://support.google.com/accounts/answer/185833))

## Relevant Properties
Fill in [properties file](https://github.com/oyekanmiayo/emailing-and-texting-script/blob/master/src/main/resources/application.properties)
1. `spring.mail.username` - Gmail address
2. `spring.mail.password` - Gmail App Password
3. `account.sid` - Account SID from Twilio Account
4. `auth.token` - Authentication token from Twilio Account
5. `from.phone.number` - Phone number from Twilio Account
6. `dispatch.cron` - Define the cron to send smses and emails to each official [here](https://github.com/oyekanmiayo/emailing-and-texting-script/blob/master/src/main/resources/communication.json). **It is currently set to send the smses and emails by 9am every day.**
7. `activate.script` - Set to true/false to activate/deactivate the script
8. `send.sms` - Set to false/true to turn off/on sending smses
9. `send.email` - Set to false/true to turn off/on sending emails

See details on how to generate a good cron for Spring [here](https://www.baeldung.com/cron-expressions)

## Deploying
1. Add Procfile (This is already done, so skip)
2. Login to Heroku (on your browser)
3. Create a new app
4. Go into the directory of this application via your terminal:
- `heroku login`
- `heroku git:remote -a {name.of.the.app.you.created}`
- `git push heroku master`

## Urgent
* Fork repo
* Fill in government official details [here](https://github.com/oyekanmiayo/emailing-and-texting-script/blob/master/src/main/resources/communication.json)
* Create PR
