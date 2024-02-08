import fetch from "node-fetch";

async function getScore(maxRetries) {

    console.log('Test suite being ran: ' + process.env.TESTS_TO_RUN);

    if (process.env.TESTS_TO_RUN == 'java-api')
    {
        const urls = ['http://java-api:8080/api/score/scores',
                    'http://test-api-service.default:8080/api/score/scores',
                    'http://localhost:8080/api/score/scores'];

        let url = urls[(maxRetries - 1) % urls.length];
        console.log(`Testing URL: ${url}`);

        async function onError(err) {
            console.log(`Remaining retries: ${maxRetries} result: ${err}`)
            maxRetries = maxRetries - 1;
            if (maxRetries == 0) {
                throw err;
            }
            await wait(3000);
            return await getScore(maxRetries);
        }
        return await fetch(url)
                .then(async function (response) {
                    console.log(await response.json())
                })
                .catch(onError);
    }
    else
    {
        console.log('Java API test skipped');
    }
    
}

function wait(delay) {
    return new Promise((resolve) => setTimeout(resolve, delay));
}

getScore(10);