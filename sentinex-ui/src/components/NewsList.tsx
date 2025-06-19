type Article = {
    title: string
    content: string
    tickers: string[]
    sentiment: number
}

export default function NewsList({ articles }: { articles: Article[] }) {
    return (
        <div className="mt-4 space-y-4">
            {articles.map((article, index) => (
                <div key={index} className="p-4 border rounded shadow bg-white">
                    <h2 className="text-lg font-bold">{article.title}</h2>
                    <p className="text-gray-700">{article.content}</p>
                    <p><strong>Tickers:</strong> {article.tickers.join(', ')}</p>
                    <p><strong>Sentiment:</strong> {article.sentiment.toFixed(2)}</p>
                </div>
            ))}
        </div>
    )
}