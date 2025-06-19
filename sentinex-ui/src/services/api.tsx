import axios from 'axios'

export async function fetchNewsSentiment(topic: string) {
    // const res = await axios.get(`http://localhost:8080/api/v1/news/news?query=${topic}`)
    const res = await axios.post(`http://localhost:8080/api/v1/news/news`, topic,
        {
            headers: {
                "Content-Type": "text/plain"
            }
        })
    return res.data
}
